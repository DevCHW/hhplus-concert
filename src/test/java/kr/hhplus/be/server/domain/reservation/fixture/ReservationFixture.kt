package kr.hhplus.be.server.domain.reservation.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation

class ReservationFixture {
    companion object {
        fun createReservation(
            seatId: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
            status: Reservation.Status = Reservation.Status.PENDING,
        ): Reservation {
            val createReservation = CreateReservation(
                seatId = seatId,
                userId = userId,
                status = status,
            )
            return Reservation.create(createReservation)
        }

        fun createCreateReservation(
            seatId: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
            status: Reservation.Status = Reservation.Status.PENDING,
        ): CreateReservation {
            return CreateReservation(
                seatId = seatId,
                userId = userId,
                status = status,
            )
        }
    }
}