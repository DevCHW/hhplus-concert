package kr.hhplus.be.server.listener

import kr.hhplus.be.server.domain.outbox.OutboxService
import kr.hhplus.be.server.domain.outbox.Topics
import kr.hhplus.be.server.domain.payment.model.event.PaymentCreateEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.util.*

@Component
class PaymentCreateEventListener(
    private val outboxService: OutboxService,
) {
    private val topic = Topics.PAYMENT_CREATE

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun before(event: PaymentCreateEvent) {
        val idempotencyKey = UUID.randomUUID().toString()
        outboxService.create(
            topic = topic,
            key = idempotencyKey,
            idempotencyKey = idempotencyKey,
            message = event,
        )
    }
}
