package kr.hhplus.be.server.domain.reservation

import kr.hhplus.be.server.domain.reservation.model.Reservation

interface ReservationRepository {
    fun isExistBySeatId(seatId: String): Boolean

    fun save(reservation: Reservation): Reservation

    fun getByStatus(status: Reservation.Status): List<Reservation>

    fun updateStatusByIdsIn(status: Reservation.Status, timeoutReservationsIds: List<String>): Int

    fun getById(reservationId: String): Reservation
}