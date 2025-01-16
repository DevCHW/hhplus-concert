package kr.hhplus.be.server.domain.reservation.model

data class ModifyReservation(
    val id: String,
    val status: Reservation.Status,
)
