package kr.hhplus.be.server.domain.payment.model.event

import kr.hhplus.be.server.domain.payment.model.Payment
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentCreateEvent(
    val id: String,
    val userId: String,
    val reservationId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(payment: Payment): PaymentCreateEvent {
            return PaymentCreateEvent(
                id = payment.id,
                userId = payment.userId,
                reservationId = payment.reservationId,
                amount = payment.amount,
                createdAt = payment.createdAt,
                updatedAt = payment.updatedAt,
            )
        }
    }
}
