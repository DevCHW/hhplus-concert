package kr.hhplus.be.server.domain.client.dto

import java.time.LocalDateTime

class ReservationCompleteDto(
    val id: String,
    val userId: String,
    val status: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
}