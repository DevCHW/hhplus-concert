package kr.hhplus.be.server.api.concert.controller.dto.response

import java.time.LocalDate

data class SchedulesResponse(
    val concertScheduleId: String,
    val date: LocalDate,
)
