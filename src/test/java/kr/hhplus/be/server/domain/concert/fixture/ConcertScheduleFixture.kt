package kr.hhplus.be.server.domain.concert.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import java.time.LocalDateTime

class ConcertScheduleFixture {

    companion object {
        fun createConcertSchedule(
            id: String = TsidCreator.getTsid().toString(),
            concertId: String = TsidCreator.getTsid().toString(),
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