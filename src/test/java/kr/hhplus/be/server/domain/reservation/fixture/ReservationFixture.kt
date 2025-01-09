package kr.hhplus.be.server.domain.reservation.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import java.time.LocalDateTime

class ReservationFixture {
    companion object {
        fun createReservation(
            seatId: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
            status: Reservation.Status = Reservation.Status.PENDING,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Reservation {
            return Reservation(
                seatId = seatId,
                userId = userId,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
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