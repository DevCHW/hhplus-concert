package kr.hhplus.be.server.infra.storage.core.payment.jpa.repository

import kr.hhplus.be.server.infra.storage.core.concert.jpa.entity.SeatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SeatEntityJpaRepository : JpaRepository<SeatEntity, String> {

    fun findByConcertScheduleId(concertScheduleId: String): List<SeatEntity>

    fun findByIdIn(seatIds: Set<String>): List<SeatEntity>
}