package kr.hhplus.be.server.api.concert.application

import com.github.f4b6a3.tsid.TsidCreator
import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.concert.SeatService
import kr.hhplus.be.server.domain.concert.fixture.SeatFixture
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("좌석 Facade 단위 테스트")
class SeatFacadeTest {
    private lateinit var seatFacade : SeatFacade
    private lateinit var seatService: SeatService
    private lateinit var reservationService: ReservationService

    @BeforeEach
    fun setup() {
        seatService = mockk()
        reservationService = mockk()
        seatFacade = SeatFacade(
            seatService = seatService,
            reservationService = reservationService,
        )
    }

    @Nested
    inner class `예약 가능한 좌석 목록 조회` {
        @Test
        fun `아직 예약이 되지 않은 좌석 목록을 반환한다`() {
            // given
            val concertScheduleId = TsidCreator.getTsid().toString()
            val seat1 = SeatFixture.createSeat(
                concertScheduleId = concertScheduleId,
            )
            val seat2 = SeatFixture.createSeat(
                concertScheduleId = concertScheduleId,
            )

            // 좌석1 예약
            val reservation = ReservationFixture.createReservation(
                seatId = seat1.id,
            )

            every { seatService.getByConcertScheduleId(concertScheduleId) }
                .returns(listOf(seat1, seat2))

            every { reservationService.getBySeatIds(listOf(seat1.id, seat2.id)) }
                .returns(listOf(reservation))

            // when
            val result = seatFacade.getAvailableSeats(concertScheduleId)

            // then
            assertThat(result)
                .hasSize(1)
                .extracting("id", "number")
                .containsExactlyInAnyOrder(
                    tuple(seat2.id, seat2.number)
                )
        }
    }
}