package kr.hhplus.be.server.domain.reservation

import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.ModifyReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.stereotype.Service
import java.time.LocalDate
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
        // 이미 예약된 좌석일 경우 예외 발생
        val exist = reservationRepository.isExistBySeatId(createReservation.seatId)
        if (exist) {
            throw CoreException(ErrorType.ALREADY_RESERVED_SEAT)
        }
        return reservationRepository.save(createReservation)
    }

    /**
     * 좌석 예약 존재 여부 조회
     */
    fun isExistBySeatId(seatId: String): Boolean {
        return reservationRepository.isExistBySeatId(seatId)
    }

    /**
     * 대기 시간 초과 예약 취소
     */
    fun cancelTimeoutReservations(
        timeoutDuration: Long,
        now: LocalDateTime
    ) {
        // 결제 대기 예약 목록 조회
        val pendingReservations = reservationRepository.getByStatus(Reservation.Status.PENDING)

        // 결제 시간 초과 예약 ID 목록 추출
        val timeoutReservationsIds = pendingReservations
            .filter { it.updatedAt.plusSeconds(timeoutDuration).isBefore(now) }
            .map { it.id }

        // 결제 시간 초과 ID 목록이 있는 경우 취소 상태로 변경
        if (timeoutReservationsIds.isNotEmpty()) {
            reservationRepository.modifyStatusByIds(Reservation.Status.CANCEL, timeoutReservationsIds)
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

    fun getCompletedReservationsByDate(date: LocalDate): List<Reservation> {
        return reservationRepository.getByDateAndStatus(date, Reservation.Status.COMPLETED)
    }
}