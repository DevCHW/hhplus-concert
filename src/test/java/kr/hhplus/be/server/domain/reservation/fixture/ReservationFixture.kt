package kr.hhplus.be.server.domain.reservation.fixture

import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.support.IdGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

class ReservationFixture {
    companion object {
        fun get(
            id: String = IdGenerator.generate(),
            seatId: String = IdGenerator.generate(),
            userId: String = IdGenerator.generate(),
            payAmount: BigDecimal = BigDecimal.valueOf(100),
            status: Reservation.Status = Reservation.Status.PENDING,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Reservation {
            return Reservation(
                id = id,
                seatId = seatId,
                userId = userId,
                status = status,
                payAmount = payAmount,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}