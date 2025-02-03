package kr.hhplus.be.server.domain.concert.fixture

import kr.hhplus.be.server.domain.concert.model.Seat
import kr.hhplus.be.server.support.IdGenerator
import java.time.LocalDateTime

class SeatFixture {
    companion object {
        fun get(
            id: String = IdGenerator.generate(),
            concertScheduleId: String = IdGenerator.generate(),
            number: Int = 1,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Seat {
            return Seat(
                id = id,
                concertScheduleId = concertScheduleId,
                number = number,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}