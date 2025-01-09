package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.controller.dto.response.ConcertSchedulesResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
class ConcertScheduleController {

    /**
     * 예약 가능 날짜 목록 조회 API
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules/available")
    fun getSchedules(
        @PathVariable("concertId") concertId: String,
    ): ApiResponse<List<ConcertSchedulesResponse>> {
        val data = listOf(
            ConcertSchedulesResponse(
                id = UUID.randomUUID().toString(),
                date = LocalDate.now(),
            ),
            ConcertSchedulesResponse(
                id = UUID.randomUUID().toString(),
                date = LocalDate.now(),
            ),
        )
        return ApiResponse.success(data)
    }
}