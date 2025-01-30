package kr.hhplus.be.server.domain.support.lock

import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReleaseLockEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun releaseLock(lockHandler: LockHandler) {
        lockHandler.unlock()
    }

}