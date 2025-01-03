package kr.hhplus.be.server.api.reservation.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.reservation.controller.dto.request.CreateReservationRequest
import kr.hhplus.be.server.api.reservation.controller.dto.response.CreateReservationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ReservationController {

    /**
     * 예약
     * TODO : 구현
     */
    @PostMapping("/api/v1/reservations")
    fun createReservation(
        @RequestBody request: CreateReservationRequest,
    ): ApiResponse<CreateReservationResponse> {
        val data = CreateReservationResponse(
            reservationId = UUID.randomUUID().toString(),
        )
        return ApiResponse.success(data)
    }

}