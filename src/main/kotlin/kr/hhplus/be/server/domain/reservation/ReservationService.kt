package kr.hhplus.be.server.domain.reservation

import kr.hhplus.be.server.domain.reservation.error.SeatAlreadyReservedException
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.ModifyReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
        return reservationRepository.save(createReservation)
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
            reservationRepository.modifyStatusByIdsIn(Reservation.Status.CANCEL, timeoutReservationsIds)
        }
    }

    /**
     * 예약 조회
     */
    fun getReservation(reservationId: String): Reservation {
        return reservationRepository.getById(reservationId)
    }

    /**
     * 예약 상태 변경
     */
    @Transactional
    fun modifyReservation(
        reservationId: String,
        status: Reservation.Status
    ): Reservation {
        return reservationRepository.modify(
            ModifyReservation(
                id = reservationId,
                status = status,
            )
        )
    }

    /**
     * 좌석 ID 목록에 해당하는 예약 목록 조회
     */
    fun getBySeatIds(seatIds: List<String>): List<Reservation> {
        return reservationRepository.getBySeatIds(seatIds)
    }
}