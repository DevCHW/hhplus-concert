package kr.hhplus.be.server.domain.reservation.fixture

import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.support.IdGenerator
import java.math.BigDecimal

class CreateReservationFixture {
    companion object {
        fun get(
            seatId: String = IdGenerator.generate(),
            userId: String = IdGenerator.generate(),
            payAmount: BigDecimal = BigDecimal.valueOf(100),
            status: Reservation.Status = Reservation.Status.PENDING,
        ): CreateReservation {
            return CreateReservation(
                seatId = seatId,
                userId = userId,
                payAmount = payAmount,
                status = status,
            )
        }
    }
}