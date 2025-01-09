package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule

interface ConcertScheduleRepository {
    fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule>
}