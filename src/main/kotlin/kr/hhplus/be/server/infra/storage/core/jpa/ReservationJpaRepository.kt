package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.reservation.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface ReservationJpaRepository : JpaRepository<Reservation, String> {
    fun existsBySeatId(seatId: String): Boolean
    fun findByStatus(status: Reservation.Status): List<Reservation>

    @Transactional
    @Modifying
    @Query(
        """
        UPDATE Reservation reservation 
        SET reservation.status = :status, reservation.updatedAt = :now
        WHERE reservation.id IN :ids
    """)
    fun updateStatusByIds(
        @Param("status") status: Reservation.Status,
        @Param("ids") ids: List<String>,
        @Param("now") now: LocalDateTime = LocalDateTime.now()
    ): Int

    fun findBySeatIdIn(seatIds: List<String>): List<Reservation>
}