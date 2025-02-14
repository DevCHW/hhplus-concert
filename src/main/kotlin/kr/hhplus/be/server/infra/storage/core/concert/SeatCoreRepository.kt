package kr.hhplus.be.server.infra.storage.core.concert

import kr.hhplus.be.server.domain.concert.model.CreateSeat
import kr.hhplus.be.server.domain.concert.model.Seat
import kr.hhplus.be.server.domain.concert.repository.SeatRepository
import kr.hhplus.be.server.infra.storage.core.concert.jpa.entity.SeatEntity
import kr.hhplus.be.server.infra.storage.core.payment.jpa.repository.SeatEntityJpaRepository
import org.springframework.stereotype.Repository

@Repository
class SeatCoreRepository(
    private val seatJpaRepository: SeatEntityJpaRepository,
) : SeatRepository {

    override fun save(createSeat: CreateSeat): Seat {
        val seatEntity = SeatEntity.create(createSeat)
        return seatJpaRepository.save(seatEntity).toDomain()
    }

    override fun getByConcertScheduleId(concertScheduleId: String): List<Seat> {
        return seatJpaRepository.findByConcertScheduleId(concertScheduleId)
            .map { it.toDomain() }
    }

    override fun getByIds(seatIds: Set<String>): List<Seat> {
        val seatEntities = seatJpaRepository.findByIdIn(seatIds)
        return seatEntities.map { it.toDomain() }
    }
}