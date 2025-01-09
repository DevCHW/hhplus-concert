package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.reservation.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationJpaRepository : JpaRepository<Reservation, String> {
    fun existsBySeatId(seatId: String): Boolean
}