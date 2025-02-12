package kr.hhplus.be.server.infra.storage.core.concert.jpa.repository

import kr.hhplus.be.server.infra.storage.core.concert.jpa.entity.ConcertScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ConcertScheduleEntityJpaRepository : JpaRepository<ConcertScheduleEntity, String> {

    fun findByConcertIdAndConcertAtAfter(concertId: String, now: LocalDateTime): List<ConcertScheduleEntity>

    fun findByIdIn(ids: Set<String>): List<ConcertScheduleEntity>
}