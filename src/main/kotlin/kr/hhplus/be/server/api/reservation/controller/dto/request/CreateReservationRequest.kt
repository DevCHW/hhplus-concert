package kr.hhplus.be.server.api.reservation.controller.dto.request

data class CreateReservationRequest(
    val concertScheduleId: String,
    val seatId: String,
)
