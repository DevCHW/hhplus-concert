package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.concert.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertScheduleJpaRepository : JpaRepository<Schedule, String> {
}