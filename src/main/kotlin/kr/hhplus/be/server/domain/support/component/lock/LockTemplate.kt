package kr.hhplus.be.server.domain.support.component.lock

import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.util.concurrent.TimeUnit

@Component
class LockTemplate(
    private val lockClients: Set<LockClient>,
    private val eventPublisher: ApplicationEventPublisher,
) {
    private val lockNamePrefix: String  = "LOCK"
    private val defaultWaitTime: Long = 5L // 기본 락 획득 대기시간
    private val defaultLeaseTime: Long = 3L // 기본 락 해제 시간
    private val defaultTimeUnit: TimeUnit = TimeUnit.SECONDS // 기본 시간 기준

    /**
     * 분산 락 실행
     */
    fun <T> withDistributedLock(
        resource: LockResource,
        key: String,
        strategy: LockStrategy,
        waitTime: Long = defaultWaitTime,
        leaseTime: Long = defaultLeaseTime,
        timeUnit: TimeUnit = defaultTimeUnit,
        action: () -> T
    ): T {
        // 전략에 해당하는 잠금 수행 구현체 선택
        val lockClient = find(strategy)

        // 데드락 방지를 위하여 스레드에서 이미 락을 획득한 적이 있다면 스킵
        val lockName = generateLockName(resource, key)
        val lockedNames = LockedNamesHolder.get()
        if (lockedNames.contains(lockName)) {
            return action()
        }

        // 락 획득
        val lockHandler = lockClient.tryLock(key, waitTime, leaseTime, timeUnit) ?: throw CoreException(ErrorType.GET_LOCK_FAIL)

        // 획득 락 이름 추가
        LockedNamesHolder.add(lockName)

        return try {
            action()
        } finally {
            // 획득 락 이름 제거
            LockedNamesHolder.remove(lockName)

            // 트랜잭션이 진행중인 경우 이벤트 발행 / 트랜잭션 진행중이 아닌 경우 바로 락 해제
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
                    override fun afterCompletion(status: Int) {
                        lockHandler.unlock()
                    }
                })
            }

            if (!TransactionSynchronizationManager.isActualTransactionActive()) {
                lockHandler.unlock()
            }

            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                eventPublisher.publishEvent(lockHandler)
            } else {
                lockHandler.unlock()
            }
        }
    }

    // 락 수행 구현체 선택
    private fun find(strategy: LockStrategy): LockClient {
        return lockClients
            .firstOrNull { it.isSupport(strategy) } ?: throw IllegalArgumentException("${strategy}전략에 해당하는 잠금 수행 구현체가 없습니다.")
    }

    // 락 이름 생성
    private fun generateLockName(resource: LockResource, key: String): String {
        return "${lockNamePrefix}:${resource.name.lowercase()}:${key}"
    }

}