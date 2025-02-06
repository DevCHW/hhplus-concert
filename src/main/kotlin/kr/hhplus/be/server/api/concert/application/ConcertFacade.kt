package kr.hhplus.be.server.api.concert.application

import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertResult
import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertsResult
import kr.hhplus.be.server.api.concert.application.dto.result.GetPopularConcertsResult
import kr.hhplus.be.server.domain.concert.ConcertScheduleService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.SeatService
import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.reservation.ReservationService
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ConcertFacade(
    private val concertService: ConcertService,
    private val reservationService: ReservationService,
    private val concertScheduleService: ConcertScheduleService,
    private val seatService: SeatService,
) {
    /**
     * 콘서트 단건 조회
     */
    fun getConcert(concertId: String): GetConcertResult {
        val concert = concertService.getConcert(concertId)
        return GetConcertResult.from(concert)
    }

    /**
     * 콘서트 목록 조회
     */
    fun getConcerts(): List<GetConcertsResult> {
        val concerts = concertService.getConcerts()
        return concerts.map { GetConcertsResult.from(it) }
    }

    /**
     * 인기 콘서트 조회
     * TODO : 구현
     */
    fun getPopularConcerts(date: LocalDate): List<GetPopularConcertsResult> {
        TODO("Not yet implemented")
    }

}