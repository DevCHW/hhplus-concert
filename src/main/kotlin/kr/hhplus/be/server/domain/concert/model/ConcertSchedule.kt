package kr.hhplus.be.server.domain.concert.model

import java.time.LocalDateTime

class ConcertSchedule(
    val id: String,
    val concertId: String,
    val location: String,
    val concertAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
}