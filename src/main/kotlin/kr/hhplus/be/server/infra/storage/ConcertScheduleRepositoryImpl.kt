package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.concert.ConcertScheduleRepository
import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.infra.storage.core.jpa.ConcertScheduleJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ConcertScheduleRepositoryImpl(
    private val concertScheduleJpaRepository: ConcertScheduleJpaRepository,
) : ConcertScheduleRepository {

    override fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule> {
        return concertScheduleJpaRepository.findByConcertIdAndConcertAtAfter(concertId, LocalDateTime.now())
    }
}