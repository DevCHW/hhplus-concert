package kr.hhplus.be.server.api.reservation.application

import kr.hhplus.be.server.api.reservation.application.dto.CreateReservationResult
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import org.springframework.stereotype.Component

@Component
class ReservationFacade(
    private val reservationService: ReservationService,
    private val concertService: ConcertService,
) {

    /**
     * 예약 생성
     */
    fun createReservation(
        concertId: String,
        userId: String,
        seatId: String,
    ): CreateReservationResult {
        // 콘서트 조회
        val concert = concertService.getConcert(concertId)

        // 예약 생성
        val reservation = reservationService.createReservation(
            CreateReservation(
                userId = userId,
                seatId = seatId,
                payAmount = concert.price
            )
        )

        return CreateReservationResult.from(reservation)
    }
}