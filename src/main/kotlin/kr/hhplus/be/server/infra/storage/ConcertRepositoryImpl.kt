package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.concert.ConcertRepository
import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.infra.storage.core.findByIdOrThrow
import kr.hhplus.be.server.infra.storage.core.jpa.ConcertJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ConcertRepositoryImpl(
    private val concertJpaRepository: ConcertJpaRepository,
) : ConcertRepository {

    override fun getById(concertId: String): Concert {
        return concertJpaRepository.findByIdOrThrow(concertId)
    }

}