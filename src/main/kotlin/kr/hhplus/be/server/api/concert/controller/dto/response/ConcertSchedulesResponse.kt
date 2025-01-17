package kr.hhplus.be.server.api.concert.controller.dto.response

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import java.time.LocalDateTime

data class ConcertSchedulesResponse(
    val id: String,
    val concertAt: LocalDateTime,
) {
    companion object {
        fun from(concertSchedule: ConcertSchedule): ConcertSchedulesResponse {
            return ConcertSchedulesResponse(
                id = concertSchedule.id,
                concertAt = concertSchedule.concertAt,
            )
        }
    }
}
