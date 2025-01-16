package kr.hhplus.be.server.infra.storage.core.jpa.repository

import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.infra.storage.core.jpa.entity.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface ReservationEntityJpaRepository : JpaRepository<ReservationEntity, String> {
    fun existsBySeatId(seatId: String): Boolean
    fun findByStatus(status: Reservation.Status): List<ReservationEntity>

    @Transactional
    @Modifying
    @Query(
        """
        UPDATE ReservationEntity reservation 
        SET reservation.status = :status, reservation.updatedAt = :now
        WHERE reservation.id IN :ids
    """
    )
    fun updateStatusByIds(
        @Param("status") status: Reservation.Status,
        @Param("ids") ids: List<String>,
        @Param("now") now: LocalDateTime = LocalDateTime.now()
    ): Int

    fun findBySeatIdIn(seatIds: List<String>): List<ReservationEntity>
}