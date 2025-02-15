package kr.hhplus.be.server.listener.event

import kr.hhplus.be.server.domain.client.dto.ReservationCompleteDto
import java.time.LocalDateTime

data class ReservationCompleteEvent(
    val id: String,
    val userId: String,
    val status: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    fun toModel(): ReservationCompleteDto {
        return ReservationCompleteDto(
            id = id,
            userId = userId,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
