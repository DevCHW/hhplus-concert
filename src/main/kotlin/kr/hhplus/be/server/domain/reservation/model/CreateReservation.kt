package kr.hhplus.be.server.domain.reservation.model

data class CreateReservation(
    val userId: String,
    val seatId: String,
    val status: Reservation.Status = Reservation.Status.PENDING
)