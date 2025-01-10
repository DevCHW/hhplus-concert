package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.controller.dto.response.ConcertSchedulesResponse
import kr.hhplus.be.server.domain.concert.ConcertScheduleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ConcertScheduleController(
    private val concertScheduleService: ConcertScheduleService,
) {

    /**
     * 예약 가능 날짜 목록 조회 API
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules/available")
    fun getSchedules(
        @PathVariable("concertId") concertId: String,
    ): ApiResponse<List<ConcertSchedulesResponse>> {
        val data = concertScheduleService.getAvailableConcertSchedules(concertId)
        return ApiResponse.success(data.map { ConcertSchedulesResponse.from(it) })
    }
}