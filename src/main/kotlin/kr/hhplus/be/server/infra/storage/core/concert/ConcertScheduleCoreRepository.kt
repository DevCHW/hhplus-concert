package kr.hhplus.be.server.infra.storage.core.concert

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.domain.concert.model.CreateConcertSchedule
import kr.hhplus.be.server.domain.concert.repository.ConcertScheduleRepository
import kr.hhplus.be.server.infra.storage.core.concert.jpa.entity.ConcertScheduleEntity
import kr.hhplus.be.server.infra.storage.core.concert.jpa.repository.ConcertScheduleEntityJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ConcertScheduleCoreRepository(
    private val concertScheduleJpaRepository: ConcertScheduleEntityJpaRepository,
) : ConcertScheduleRepository {

    override fun save(createConcertSchedule: CreateConcertSchedule): ConcertSchedule {
        val concertScheduleEntity = ConcertScheduleEntity.create(createConcertSchedule)
        return concertScheduleJpaRepository.save(concertScheduleEntity).toDomain()
    }

    override fun getAvailableConcertSchedules(concertId: String): List<ConcertSchedule> {
        return concertScheduleJpaRepository.findByConcertIdAndConcertAtAfter(concertId, LocalDateTime.now())
            .map { it.toDomain() }
    }

    override fun getByIds(concertScheduleIds: Set<String>): List<ConcertSchedule> {
        val concertScheduleEntities = concertScheduleJpaRepository.findByIdIn(concertScheduleIds)
        return concertScheduleEntities.map { it.toDomain() }
    }
}