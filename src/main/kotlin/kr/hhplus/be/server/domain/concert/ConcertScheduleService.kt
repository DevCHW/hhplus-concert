package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.domain.concert.repository.ConcertScheduleRepository
import org.springframework.stereotype.Service

@Service
class ConcertScheduleService(
    private val concertScheduleRepository: ConcertScheduleRepository
) {

    fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule> {
        return concertScheduleRepository.getAvailableConcertSchedules(concertId)
    }

    fun getConcertSchedulesByIds(concertScheduleIds: Set<String>): List<ConcertSchedule> {
        return concertScheduleRepository.getByIds(concertScheduleIds)
    }
}