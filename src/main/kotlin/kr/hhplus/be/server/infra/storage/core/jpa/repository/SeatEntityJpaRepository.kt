package kr.hhplus.be.server.infra.storage.core.jpa.repository

import kr.hhplus.be.server.infra.storage.core.jpa.entity.SeatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SeatEntityJpaRepository : JpaRepository<SeatEntity, String> {

    fun findByConcertScheduleId(concertScheduleId: String): List<SeatEntity>

}