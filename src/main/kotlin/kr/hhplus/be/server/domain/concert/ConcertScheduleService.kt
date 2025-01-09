package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import org.springframework.stereotype.Service

@Service
class ConcertScheduleService(
    private val concertScheduleRepository: ConcertScheduleRepository
) {

    fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule> {
        return concertScheduleRepository.getAvailableConcertSchedules(concertId)
    }
}