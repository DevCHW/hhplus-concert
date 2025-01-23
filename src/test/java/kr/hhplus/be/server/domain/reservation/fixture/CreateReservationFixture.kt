package kr.hhplus.be.server.domain.reservation.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import java.math.BigDecimal

class CreateReservationFixture {
    companion object {
        fun get(
            seatId: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
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