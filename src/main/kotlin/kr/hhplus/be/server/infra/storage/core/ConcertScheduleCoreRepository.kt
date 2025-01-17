package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.concert.ConcertScheduleRepository
import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.domain.concert.model.CreateConcertSchedule
import kr.hhplus.be.server.infra.storage.core.jpa.entity.ConcertScheduleEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.ConcertScheduleEntityJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ConcertScheduleCoreRepository(
    private val concertScheduleJpaRepository: ConcertScheduleEntityJpaRepository,
) : ConcertScheduleRepository {

    override fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule> {
        return concertScheduleJpaRepository.findByConcertIdAndConcertAtAfter(concertId, LocalDateTime.now())
            .map { it.toDomain() }
    }

    override fun save(createConcertSchedule: CreateConcertSchedule): ConcertSchedule {
        val concertScheduleEntity = ConcertScheduleEntity.create(createConcertSchedule)
        return concertScheduleJpaRepository.save(concertScheduleEntity).toDomain()
    }
}