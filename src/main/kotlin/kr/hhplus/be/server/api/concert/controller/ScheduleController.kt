package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.controller.dto.response.SchedulesResponse
import kr.hhplus.be.server.api.concert.controller.enums.ScheduleStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RestController
class ScheduleController {

    /**
     * 예약 가능 날짜 목록 조회 API
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules")
    fun getSchedules(
        @PathVariable("concertId") concertId: String,
        @RequestParam("status") status: ScheduleStatus,
    ): ApiResponse<List<SchedulesResponse>> {
        val data = listOf(
            SchedulesResponse(
                concertScheduleId = UUID.randomUUID().toString(),
                date = LocalDate.now(),
            ),
            SchedulesResponse(
                concertScheduleId = UUID.randomUUID().toString(),
                date = LocalDate.now(),
            ),
        )
        return ApiResponse.success(data)
    }
}