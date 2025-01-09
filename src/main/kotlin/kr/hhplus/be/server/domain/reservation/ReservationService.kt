package kr.hhplus.be.server.domain.reservation

import kr.hhplus.be.server.domain.reservation.error.SeatAlreadyReservedException
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
) {

    /**
     * 예약 생성
     */
    fun createReservation(
        createReservation: CreateReservation
    ): Reservation {
        val exist = reservationRepository.isExistBySeatId(createReservation.seatId)
        if (exist) {
            throw SeatAlreadyReservedException("이미 예약된 좌석입니다.")
        }

        val reservation = Reservation.create(createReservation)

        return reservationRepository.save(reservation)
    }

    /**
     * 대기 시간 초과 예약 취소
     */
    fun cancelTimeoutReservations(
        timeoutDuration: Long,
        now: LocalDateTime
    ) {
        val pendingReservations = reservationRepository.getByStatus(Reservation.Status.PENDING)
        val timeoutReservationsIds = pendingReservations
            .filter { it.updatedAt.plusSeconds(timeoutDuration).isBefore(now) }
            .map { it.id }

        if (timeoutReservationsIds.isNotEmpty()) {
            reservationRepository.updateStatusByIdsIn(Reservation.Status.CANCEL, timeoutReservationsIds)
        }
    }
}