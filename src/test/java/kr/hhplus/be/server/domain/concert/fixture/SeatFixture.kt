package kr.hhplus.be.server.domain.concert.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.concert.model.Seat

class SeatFixture {
    companion object {
        fun createSeat(
            concertScheduleId: String = TsidCreator.getTsid().toString(),
            number: Int = 1,
        ): Seat {
            return Seat(
                concertScheduleId = concertScheduleId,
                number = number,
            )
        }
    }
}