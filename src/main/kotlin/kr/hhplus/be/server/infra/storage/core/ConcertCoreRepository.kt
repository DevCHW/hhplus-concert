package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.concert.ConcertRepository
import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.concert.model.CreateConcert
import kr.hhplus.be.server.infra.storage.core.jpa.entity.ConcertEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.ConcertEntityJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ConcertCoreRepository(
    private val concertJpaRepository: ConcertEntityJpaRepository,
) : ConcertRepository {

    override fun getById(concertId: String): Concert {
        return concertJpaRepository.findByIdOrThrow(concertId).toDomain()
    }

    override fun save(createConcert: CreateConcert): Concert {
        val concertEntity = ConcertEntity.create(createConcert)
        return concertJpaRepository.save(concertEntity).toDomain()
    }

}