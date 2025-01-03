package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.controller.dto.response.SeatsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class SeatController {

    /**
     * 예약 가능 좌석 목록 조회
     * TODO : 구현
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules/{scheduleId}/seats")
    fun getSeats(
        @RequestParam("status") status: String,
        @PathVariable("concertId") concertId: String,
        @PathVariable("scheduleId") scheduleId: String,
    ): ApiResponse<List<SeatsResponse>> {
        val data = listOf(
            SeatsResponse(
                seatId = UUID.randomUUID().toString(),
                number = 1
            ),
            SeatsResponse(
                seatId = UUID.randomUUID().toString(),
                number = 2
            ),
        )
        return ApiResponse.success(data)
    }
}