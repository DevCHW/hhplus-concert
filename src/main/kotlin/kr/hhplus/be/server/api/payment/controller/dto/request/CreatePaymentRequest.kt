package kr.hhplus.be.server.api.payment.controller.dto.request

import java.util.*

data class CreatePaymentRequest(
    val reservationId: String,
    val token: UUID,
)
