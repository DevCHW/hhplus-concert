package kr.hhplus.be.server.api.concert.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.concert.application.ConcertFacade
import kr.hhplus.be.server.api.concert.controller.dto.response.GetConcertsResponse
import kr.hhplus.be.server.api.concert.controller.dto.response.GetPopularConcertsResponse
import kr.hhplus.be.server.domain.concert.ConcertService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class ConcertController(
    private val concertFacade: ConcertFacade
) {

    /**
     * 콘서트 목록 조회
     */
    @GetMapping("/api/v1/concerts")
    fun getConcerts(): ApiResponse<List<GetConcertsResponse>> {
        val data = concertFacade.getConcerts()
        return ApiResponse.success(
            data.map { GetConcertsResponse.from(it) }
        )
    }

    /**
     * 인기 콘서트 목록 조회
     * TODO : 1. 구현, 일간 예약 많은 인기 콘서트 조회
     * TODO : 2. 캐싱 처리
     */
    @GetMapping("/concerts/popular")
    fun getPopularConcerts(
        @RequestParam date: LocalDate
    ): ApiResponse<List<GetPopularConcertsResponse>> {
        val data = concertFacade.getPopularConcerts(date)
        return ApiResponse.success(
            data.map { GetPopularConcertsResponse.from(it) }
        )
    }
}