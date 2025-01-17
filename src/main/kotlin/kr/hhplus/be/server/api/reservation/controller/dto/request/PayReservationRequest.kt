package kr.hhplus.be.server.api.reservation.controller.dto.request

import java.util.*

data class PayReservationRequest(
    val reservationId: String,
    val token: UUID,
)
