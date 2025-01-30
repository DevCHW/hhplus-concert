package kr.hhplus.be.server.domain.support.lock

import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.util.concurrent.TimeUnit

@Component
class LockTemplate(
    private val distributedLockClients: Map<String, DistributedLockClient>,
    private val eventPublisher: ApplicationEventPublisher,
) {

    private val lockedNamesHolder: ThreadLocal<MutableList<String>> = ThreadLocal.withInitial { mutableListOf() }
    private val lockNamePrefix: String  = "LOCK:"
    private val defaultWaitTime: Long = 5L // 기본 락 획득 대기시간
    private val defaultLeaseTime: Long = 3L // 기본 락 해제 시간
    private val defaultTimeUnit: TimeUnit = TimeUnit.SECONDS // 기본 시간 기준

    init {
        validateDistributedLockClients()
    }

    /**
     * 분산 락 실행
     */
    fun <T> withDistributedLock(
        resource: LockResource,
        key: String,
        strategy: LockStrategy,
        waitTime: Long = defaultWaitTime,
        releaseTime: Long = defaultLeaseTime,
        timeUnit: TimeUnit = defaultTimeUnit,
        action: () -> T
    ): T {
        // 분산락 전략 선택
        val lockClient = distributedLockClients[strategy.clientName] ?: throw IllegalStateException("분산 락 전략에 해당하는 구현체가 없습니다. strategyName=$strategy.strategyName")

        // 데드락 방지를 위하여 스레드에서 이미 락을 획득한 적이 있다면 스킵
        val lockName = generateLockName(resource, key)
        val lockedNames = lockedNamesHolder.get()
        if (lockedNames.contains(lockName)) {
            return action()
        }

        // 락 획득
        val lockHandler = lockClient.tryLock(key, waitTime, releaseTime, timeUnit) ?: throw CoreException(ErrorType.GET_LOCK_FAIL)

        // 획득 락 이름 추가
        lockedNames.add(lockName)

        // 트랜잭션이 진행중인 경우
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            return try {
                action()
            } finally {
                lockedNamesHolder.remove()
                eventPublisher.publishEvent(lockHandler)
            }
        }

        // 트랜잭션이 진행중이지 않은 경우
        lockHandler.use {
            val result = action()
            lockedNamesHolder.remove()
            return result
        }
    }

    // 주입받은 <String, DistributedClient> Map 검증
    private fun validateDistributedLockClients() {
        val lockStrategies = LockStrategy.entries.map { it.clientName }.toSet()

        // distributedLockClients에 존재하는 키가 validTypes에 포함되지 않은 경우 예외 발생
        distributedLockClients.keys.forEach { lockClientName ->
            if (!lockStrategies.contains(lockClientName)) {
                throw IllegalStateException("유효하지 않은 분산락 전략입니다. lockClientName=[$lockClientName]")
            }
        }
    }

    // Lock 이름 생성
    private fun generateLockName(resource: LockResource, suffix: String): String {
        val prefix = "${lockNamePrefix}${resource.name.lowercase()}:"
        return prefix + suffix
    }

}