package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.Seat

interface SeatRepository {
    fun getByConcertScheduleId(concertScheduleId: String): List<Seat>

    fun save(seat: Seat): Seat
}