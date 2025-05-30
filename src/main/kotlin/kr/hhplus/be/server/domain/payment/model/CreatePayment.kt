package kr.hhplus.be.server.domain.payment.model

import java.math.BigDecimal

data class CreatePayment(
    val userId: String,
    val reservationId: String,
    val amount: BigDecimal,
) {

}