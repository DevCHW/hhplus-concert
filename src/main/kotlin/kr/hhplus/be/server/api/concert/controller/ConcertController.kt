package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.controller.dto.response.GetConcertsResponse
import kr.hhplus.be.server.domain.concert.ConcertService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConcertController(
    private val concertService: ConcertService
) {

    /**
     * 콘서트 목록 조회
     */
    @GetMapping("/api/v1/concerts")
    fun getConcerts(): ApiResponse<List<GetConcertsResponse>> {
        val data = concertService.getConcerts()
        return ApiResponse.success(
            data.map { GetConcertsResponse.from(it) }
        )
    }
}