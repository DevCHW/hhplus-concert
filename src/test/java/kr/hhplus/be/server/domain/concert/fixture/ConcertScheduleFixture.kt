package kr.hhplus.be.server.domain.concert.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import java.time.LocalDateTime

class ConcertScheduleFixture {

    companion object {
        fun createConcertSchedule(
            concertId: String = TsidCreator.getTsid().toString(),
            concertAt: LocalDateTime = LocalDateTime.now(),
            location: String = "상암월드컵경기장",
        ): ConcertSchedule {
            return ConcertSchedule(
                concertId = concertId,
                concertAt = concertAt,
                location = location,
            )
        }
    }
}