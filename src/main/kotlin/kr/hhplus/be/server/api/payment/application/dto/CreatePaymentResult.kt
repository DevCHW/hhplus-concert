package kr.hhplus.be.server.api.payment.application.dto

import kr.hhplus.be.server.domain.payment.model.Payment
import java.math.BigDecimal

data class CreatePaymentResult(
    val paymentId: String,
    val reservationId: String,
    val amount: BigDecimal,
) {
    companion object {
        fun from(payment: Payment): CreatePaymentResult {
            return CreatePaymentResult(
                paymentId = payment.id,
                reservationId = payment.reservationId,
                amount = payment.amount,
            )
        }
    }
}