package kr.hhplus.be.server.api.reservation.application.dto

import kr.hhplus.be.server.domain.payment.model.Payment
import java.math.BigDecimal

data class PayReservationResult(
    val paymentId: String,
    val reservationId: String,
    val amount: BigDecimal,
) {
    companion object {
        fun from(payment: Payment): PayReservationResult {
            return PayReservationResult(
                paymentId = payment.id,
                reservationId = payment.reservationId,
                amount = payment.amount,
            )
        }
    }
}