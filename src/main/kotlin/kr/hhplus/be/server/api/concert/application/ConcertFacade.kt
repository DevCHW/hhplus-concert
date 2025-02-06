package kr.hhplus.be.server.api.concert.application

import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertResult
import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertsResult
import kr.hhplus.be.server.api.concert.application.dto.result.GetPopularConcertsResult
import kr.hhplus.be.server.domain.concert.ConcertScheduleService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.SeatService
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
     */
    fun getPopularConcerts(date: LocalDate): List<GetPopularConcertsResult> {
        // TODO : 캐시에서 조회

        // TODO : 캐시 히트인 경우 바로 반환

        // 날짜에 해당하는 모든 예약 건 조회
        val reservations = reservationService.getCompletedReservationsByDate(date)
        val seatIds = reservations.map { it.seatId }.toSet()

        // 예약에 해당하는 좌석 목록 조회
        val seats = seatService.getSeatByIds(seatIds)
        val seatMap = seats.associateBy { it.id }
        val concertScheduleIds = seats.map { it.concertScheduleId }.toSet()

        // 예약에 해당하는 콘서트날짜 목록 조회
        val concertSchedules = concertScheduleService.getConcertSchedulesByIds(concertScheduleIds)
        val concertScheduleMap = concertSchedules.associateBy { it.id }
        val concertIds = concertSchedules.map { it.concertId }.toSet()

        // 콘서트 날짜 목록에 해당하는 콘서트 목록 조회
        val concerts = concertService.getConcertsByIds(concertIds)
        val concertMap = concerts.associateBy { it.id }

        // <콘서트ID, 예약 건수> Map 가공
        val concertReservationCountMap = reservations
            .groupingBy { concertMap[concertScheduleMap[seatMap[it.seatId]?.concertScheduleId]?.concertId]?.id }
            .eachCount()

        // TODO : 캐시에 저장, TTL 1 day

        // 가공해서 반환
        return concertReservationCountMap.entries
            .sortedByDescending { it.value } // 예약 건수 내림차순 정렬
            .take(20) // 상위 20개
            .mapNotNull { (concertId, reservationCount) ->
                concertMap[concertId]?.let { concert ->
                    GetPopularConcertsResult.of(concert, reservationCount)
                }
            }
    }
}