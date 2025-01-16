package kr.hhplus.be.server.api.reservation.controller.dto.request

import java.util.*

data class CreatePaymentRequest(
    val reservationId: String,
    val token: UUID,
)
