package kr.hhplus.be.server.domain.reservation.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.reservation.model.Reservation
import java.math.BigDecimal
import java.time.LocalDateTime

class ReservationFixture {
    companion object {
        fun get(
            id: String = TsidCreator.getTsid().toString(),
            seatId: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
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