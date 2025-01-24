package kr.hhplus.be.server.domain.support.lock

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReleaseLockEventListener {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun releaseLock(lockResourceManager: LockResourceManager) {
        try {
            lockResourceManager.unlock()
        } catch (e: Exception) {
            log.error("[FATAL ERROR] 락 해제에 실패하였습니다.")
        }
    }
}