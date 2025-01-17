package kr.hhplus.be.server.domain.concert.model

import java.time.LocalDateTime

data class CreateConcertSchedule(
    val concertId: String,
    val location: String,
    val concertAt: LocalDateTime,
)