package kr.hhplus.be.server.domain.support.lock

import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.util.concurrent.TimeUnit

@Component
class LockTemplate(
    private val distributedLockClients: MutableMap<String, out DistributedLockClient>,
    private val eventPublisher: ApplicationEventPublisher,
) {
    private val DEFAULT_WAIT_TIME = 5L // 기본 락 획득 대기시간
    private val DEFAULT_LEASE_TIME = 3L // 기본 락 해제 시간
    private val DEFAULT_TIME_UNIT = TimeUnit.SECONDS // 기본 시간 기준

    init {
        validateDistributedLockClients()
    }

    /**
     * 분산 락 실행
     */
    fun <T> withDistributedLock(
        lockName: String,
        strategy: LockStrategy,
        waitTime: Long = DEFAULT_WAIT_TIME,
        releaseTime: Long = DEFAULT_LEASE_TIME,
        timeUnit: TimeUnit = DEFAULT_TIME_UNIT,
        block: () -> T
    ): T {
        val lockClient = distributedLockClients[strategy.clientName] ?: throw IllegalStateException("분산 락 전략에 해당하는 구현체가 없습니다. strategyName=$strategy.strategyName")

        val lockResourceManager = lockClient.getLock(lockName, waitTime, releaseTime, timeUnit) ?: throw CoreException(ErrorType.GET_LOCK_FAIL)
        // 트랜잭션이 진행중이지 않은 경우
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            lockResourceManager.use {
                val result = block()
                lockNamesHolder.remove()
                return result
            }
        }

        // 트랜잭션이 진행중인 경우
        return try {
            block()
        } finally {
            lockNamesHolder.remove()
            eventPublisher.publishEvent(lockResourceManager)
        }
    }

    // 주입받은 <String, DistributedClient> Map의
    private fun validateDistributedLockClients() {
        val lockStrategies = LockStrategy.entries.map { it.clientName }.toSet()

        // distributedLocks에 존재하는 키가 validTypes에 포함되지 않은 경우 예외 발생
        distributedLockClients.keys.forEach { lockClientName ->
            if (!lockStrategies.contains(lockClientName)) {
                throw IllegalStateException("유효하지 않은 분산락 전략입니다. lockClientName=[$lockClientName]")
            }
        }
    }
}