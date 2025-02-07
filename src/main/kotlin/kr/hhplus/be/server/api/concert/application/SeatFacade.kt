package kr.hhplus.be.server.api.concert.application

import kr.hhplus.be.server.api.concert.application.dto.result.GetAvailableSeatResult
import kr.hhplus.be.server.domain.concert.SeatService
import kr.hhplus.be.server.domain.reservation.ReservationService
import org.springframework.stereotype.Component

@Component
class SeatFacade(
    private val seatService: SeatService,
    private val reservationService: ReservationService,
) {
    fun getAvailableSeats(concertScheduleId: String): List<GetAvailableSeatResult> {
        // 좌석 목록 조회
        val seats = seatService.getByConcertScheduleId(concertScheduleId)

        // 좌석 ID 목록 추출
        val seatIds = seats.map { it.id }

        // 좌석 ID에 해당하는 예약 목록 조회
        val reservations = reservationService.getBySeatIds(seatIds)

        // 좌석 ID, 예약 Map 추출
        val reservationMap = reservations.associateBy { it.seatId }

        return seats
            .filter { reservationMap.get(it.id) == null }
            .map(GetAvailableSeatResult.Companion::from)
    }
}