package kr.hhplus.be.server.infra.storage.core.concert.jpa.repository

import kr.hhplus.be.server.infra.storage.core.concert.jpa.entity.ConcertEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertEntityJpaRepository : JpaRepository<ConcertEntity, String> {

    fun findByIdIn(concertIds: Set<String>): List<ConcertEntity>

}