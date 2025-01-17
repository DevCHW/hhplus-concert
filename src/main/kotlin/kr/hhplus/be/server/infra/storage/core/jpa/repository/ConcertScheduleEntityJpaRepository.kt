package kr.hhplus.be.server.infra.storage.core.jpa.repository

import kr.hhplus.be.server.infra.storage.core.jpa.entity.ConcertScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ConcertScheduleEntityJpaRepository : JpaRepository<ConcertScheduleEntity, String> {
    fun findByConcertIdAndConcertAtAfter(concertId: String, now: LocalDateTime): List<ConcertScheduleEntity>
}