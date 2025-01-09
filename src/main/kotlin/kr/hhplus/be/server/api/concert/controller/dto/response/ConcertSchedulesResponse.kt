package kr.hhplus.be.server.api.concert.controller.dto.response

import java.time.LocalDate

data class ConcertSchedulesResponse(
    val id: String,
    val date: LocalDate,
)
