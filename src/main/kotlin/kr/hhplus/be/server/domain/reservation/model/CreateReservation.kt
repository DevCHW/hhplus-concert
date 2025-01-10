package kr.hhplus.be.server.domain.reservation.model

import java.math.BigDecimal

data class CreateReservation(
    val userId: String,
    val seatId: String,
    val payAmount: BigDecimal,
    val status: Reservation.Status = Reservation.Status.PENDING
)