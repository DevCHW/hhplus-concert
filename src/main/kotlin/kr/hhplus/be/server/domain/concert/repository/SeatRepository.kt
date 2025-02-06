package kr.hhplus.be.server.domain.concert.repository

import kr.hhplus.be.server.domain.concert.model.CreateSeat
import kr.hhplus.be.server.domain.concert.model.Seat

interface SeatRepository {
    fun getByConcertScheduleId(concertScheduleId: String): List<Seat>

    fun save(createSeat: CreateSeat): Seat

    fun getByIds(seatIds: Set<String>): List<Seat>
}