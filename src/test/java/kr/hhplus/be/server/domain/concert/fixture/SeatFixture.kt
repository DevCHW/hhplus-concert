package kr.hhplus.be.server.domain.concert.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.concert.model.Seat
import java.time.LocalDateTime

class SeatFixture {
    companion object {
        fun createSeat(
            id: String = TsidCreator.getTsid().toString(),
            concertScheduleId: String = TsidCreator.getTsid().toString(),
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