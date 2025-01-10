package kr.hhplus.be.server.api.reservation.application.dto

import kr.hhplus.be.server.domain.reservation.model.Reservation
import java.time.LocalDateTime

data class CreateReservationResult(
    val id: String,
    val userId: String,
    val seatId: String,
    val status: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(reservation: Reservation): CreateReservationResult {
            return CreateReservationResult(
                id = reservation.id,
                userId = reservation.userId,
                seatId = reservation.seatId,
                status = reservation.status.name,
                createdAt = reservation.createdAt,
                updatedAt = reservation.updatedAt,
            )
        }
    }
}
