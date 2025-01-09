package kr.hhplus.be.server.api.payment.controller.dto.request

import java.util.*

data class PaymentRequest(
    val reservationId: String,
    val token: UUID,
)
