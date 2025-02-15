package kr.hhplus.be.server.infra.storage.core.concert

import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.concert.model.CreateConcert
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository
import kr.hhplus.be.server.infra.storage.core.concert.jpa.entity.ConcertEntity
import kr.hhplus.be.server.infra.storage.core.concert.jpa.repository.ConcertEntityJpaRepository
import kr.hhplus.be.server.infra.storage.core.utils.findByIdOrThrow
import org.springframework.stereotype.Repository

@Repository
class ConcertCoreRepository(
    private val concertJpaRepository: ConcertEntityJpaRepository,
) : ConcertRepository {

    override fun save(createConcert: CreateConcert): Concert {
        val concertEntity = ConcertEntity.create(createConcert)
        return concertJpaRepository.save(concertEntity).toDomain()
    }

    override fun getById(concertId: String): Concert {
        return concertJpaRepository.findByIdOrThrow(concertId).toDomain()
    }

    override fun getAll(): List<Concert> {
        return concertJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun getByIds(concertIds: Set<String>): List<Concert> {
        val concertEntities = concertJpaRepository.findByIdIn(concertIds)
        return concertEntities.map { it.toDomain() }
    }

}