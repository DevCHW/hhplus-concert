package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.concert.SeatRepository
import kr.hhplus.be.server.domain.concert.model.Seat
import kr.hhplus.be.server.infra.storage.core.jpa.SeatJpaRepository
import org.springframework.stereotype.Repository

@Repository
class SeatRepositoryImpl(
    private val seatJpaRepository: SeatJpaRepository,
) : SeatRepository {

    override fun getByConcertScheduleId(concertScheduleId: String): List<Seat> {
        return seatJpaRepository.findByConcertScheduleId(concertScheduleId)
    }
}