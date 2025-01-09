package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.application.SeatFacade
import kr.hhplus.be.server.api.concert.controller.dto.response.SeatsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SeatController(
    private val seatFacade: SeatFacade,
) {

    /**
     * 예약 가능 좌석 목록 조회
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules/{concertScheduleId}/seats/available")
    fun getSeats(
        @PathVariable("concertId") concertId: String,
        @PathVariable("concertScheduleId") concertScheduleId: String,
    ): ApiResponse<List<SeatsResponse>> {
        val data = seatFacade.getAvailableSeats(concertScheduleId)
        return ApiResponse.success(data.map { SeatsResponse.from(it) })
    }
}