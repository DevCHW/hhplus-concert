package kr.hhplus.be.server.domain.concert.model

import java.time.LocalDateTime

data class Seat(
    val id: String,
    val concertScheduleId: String,
    val number: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
