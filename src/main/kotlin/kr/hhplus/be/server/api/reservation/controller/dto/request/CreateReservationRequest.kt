package kr.hhplus.be.server.api.reservation.controller.dto.request

data class CreateReservationRequest(
    val concertId: String,
    val userId: String,
    val seatId: String,
)
