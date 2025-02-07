package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.Seat
import kr.hhplus.be.server.domain.concert.repository.SeatRepository
import org.springframework.stereotype.Service

@Service
class SeatService(
    private val seatRepository: SeatRepository,
) {
    /**
     * 콘서트 일정 ID에 해당하는 좌석 목록 조회
     */
    fun getByConcertScheduleId(concertScheduleId: String): List<Seat> {
        return seatRepository.getByConcertScheduleId(concertScheduleId)
    }

    fun getSeatByIds(seatIds: Set<String>): List<Seat> {
        return seatRepository.getByIds(seatIds)
    }
}