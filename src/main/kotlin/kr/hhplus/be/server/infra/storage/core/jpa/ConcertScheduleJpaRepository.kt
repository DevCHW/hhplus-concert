package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ConcertScheduleJpaRepository : JpaRepository<ConcertSchedule, String> {
    fun findByConcertIdAndConcertAtAfter(concertId: String, now: LocalDateTime): List<ConcertSchedule>
}