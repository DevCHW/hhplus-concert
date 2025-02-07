package kr.hhplus.be.server.domain.concert.fixture

import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.support.IdGenerator
import java.time.LocalDateTime

class ConcertScheduleFixture {

    companion object {
        fun get(
            id: String = IdGenerator.generate(),
            concertId: String = IdGenerator.generate(),
            concertAt: LocalDateTime = LocalDateTime.now(),
            location: String = "상암월드컵경기장",
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): ConcertSchedule {
            return ConcertSchedule(
                id = id,
                concertId = concertId,
                concertAt = concertAt,
                location = location,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}