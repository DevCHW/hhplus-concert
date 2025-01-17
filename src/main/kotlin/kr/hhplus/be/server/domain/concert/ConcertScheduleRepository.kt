package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.domain.concert.model.CreateConcertSchedule

interface ConcertScheduleRepository {
    fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule>
    fun save(createConcertSchedule: CreateConcertSchedule): ConcertSchedule
}