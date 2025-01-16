package kr.hhplus.be.server.domain.concert.model

data class CreateSeat(
    val concertScheduleId: String,
    val number: Int,
)
