package kr.hhplus.be.server.api.concert.controller.dto.response

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import java.time.LocalDate

data class ConcertSchedulesResponse(
    val id: String,
    val date: LocalDate,
) {
    companion object {
        fun from(concertSchedule: ConcertSchedule): ConcertSchedulesResponse {
            return ConcertSchedulesResponse(
                id = concertSchedule.id,
                date = concertSchedule.concertAt.toLocalDate(),
            )
        }
    }
}
