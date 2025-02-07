package kr.hhplus.be.server.domain.reservation

import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.ModifyReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import java.time.LocalDate

interface ReservationRepository {
    fun isExistBySeatId(seatId: String): Boolean

    fun save(createReservation: CreateReservation): Reservation

    fun getByStatus(status: Reservation.Status): List<Reservation>

    fun modifyStatusByIds(status: Reservation.Status, reservationIds: List<String>): Int

    fun modify(modifyReservation: ModifyReservation): Reservation

    fun getById(reservationId: String): Reservation

    fun getBySeatIds(seatIds: List<String>): List<Reservation>

    fun getByIdWithLock(reservationId: String): Reservation

    fun getByDateAndStatus(date: LocalDate, status: Reservation.Status): List<Reservation>
}