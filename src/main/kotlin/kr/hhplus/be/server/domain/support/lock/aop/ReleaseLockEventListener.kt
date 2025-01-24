package kr.hhplus.be.server.domain.support.lock.aop

import kr.hhplus.be.server.domain.support.lock.LockResourceManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReleaseLockEventListener {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun releaseLock(lockResourceManager: LockResourceManager) {
        try {
            lockResourceManager.unlock()
        } catch (e: Exception) {
            log.error("[FATAL ERROR] 락 해제에 실패하였습니다.")
        }
    }
}