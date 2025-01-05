package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.concert.ConcertSchedule
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertScheduleJpaRepository : JpaRepository<ConcertSchedule, String> {
}