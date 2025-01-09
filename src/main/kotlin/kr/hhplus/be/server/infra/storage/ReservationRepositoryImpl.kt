package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.reservation.ReservationRepository
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.infra.storage.core.findByIdOrThrow
import kr.hhplus.be.server.infra.storage.core.jpa.ReservationJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ReservationRepositoryImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationRepository {

    override fun isExistBySeatId(seatId: String): Boolean {
        return reservationJpaRepository.existsBySeatId(seatId)
    }

    override fun save(reservation: Reservation): Reservation {
        return reservationJpaRepository.save(reservation)
    }

    override fun getByStatus(status: Reservation.Status): List<Reservation> {
        return reservationJpaRepository.findByStatus(status)
    }

    override fun updateStatusByIdsIn(status: Reservation.Status, timeoutReservationsIds: List<String>): Int {
        return reservationJpaRepository.updateStatusByIds(status, timeoutReservationsIds)
    }

    override fun getById(reservationId: String): Reservation {
        return reservationJpaRepository.findByIdOrThrow(reservationId)
    }

    override fun getBySeatIds(seatIds: List<String>): List<Reservation> {
        return reservationJpaRepository.findBySeatIdIn(seatIds)
    }
}