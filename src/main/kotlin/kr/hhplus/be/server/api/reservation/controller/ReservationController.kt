package kr.hhplus.be.server.api.reservation.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.reservation.application.ReservationFacade
import kr.hhplus.be.server.api.reservation.controller.dto.request.CreateReservationRequest
import kr.hhplus.be.server.api.reservation.controller.dto.request.PayReservationRequest
import kr.hhplus.be.server.api.reservation.controller.dto.response.CreateReservationResponse
import kr.hhplus.be.server.api.reservation.controller.dto.response.PayReservationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController(
    private val reservationFacade: ReservationFacade,
) {

    /**
     * 예약 API
     */
    @PostMapping("/api/v1/reservations")
    fun createReservation(
        @RequestBody request: CreateReservationRequest,
    ): ApiResponse<CreateReservationResponse> {
        val result = reservationFacade.createReservation(
            request.concertId,
            request.userId,
            request.seatId,
        )

        return ApiResponse.success(CreateReservationResponse(result.id))
    }

    /**
     * 예약 결제 API
     */
    @PostMapping("/api/v1/reservations/pay")
    fun payReservation(
        @RequestBody request: PayReservationRequest
    ): ApiResponse<PayReservationResponse> {
        val result = reservationFacade.payReservation(request.reservationId, request.token)
        return ApiResponse.success(PayReservationResponse(result.paymentId))
    }

}