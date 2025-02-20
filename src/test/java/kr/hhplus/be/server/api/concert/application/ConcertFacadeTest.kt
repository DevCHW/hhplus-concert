package kr.hhplus.be.server.api.concert.application

import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertResult
import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertsResult
import kr.hhplus.be.server.domain.concert.ConcertScheduleService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.SeatService
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.domain.reservation.ReservationService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ConcertFacadeTest {
    private lateinit var concertService: ConcertService
    private lateinit var reservationService: ReservationService
    private lateinit var concertScheduleService: ConcertScheduleService
    private lateinit var seatService: SeatService
    private lateinit var concertFacade: ConcertFacade

    @BeforeEach
    fun setup() {
        concertService = mockk()
        reservationService = mockk()
        concertScheduleService = mockk()
        seatService = mockk()

        concertFacade = ConcertFacade(
            concertService = concertService,
            reservationService = reservationService,
            concertScheduleService = concertScheduleService,
            seatService = seatService,
        )
    }

    @Nested
    inner class `콘서트 단건 조회` {
        @Test
        fun `조회한 데이터를 통해 적절한 타입으로 변환하여 반환한다`() {
            // given
            val concert = ConcertFixture.get()
            every { concertService.getConcert(any()) } returns concert

            // when
            val result = concertFacade.getConcert(concert.id)

            // then
            assertThat(result).isInstanceOf(GetConcertResult::class.java)
        }
    }

    @Nested
    inner class `콘서트 목록 조회` {
        @Test
        fun `조회한 데이터를 통해 적절한 타입으로 변환하여 반환한다`() {
            // given
            val concerts = listOf(
                ConcertFixture.get(),
                ConcertFixture.get(),
                ConcertFixture.get(),
            )
            every { concertService.getConcerts() } returns concerts

            // when
            val result = concertFacade.getConcerts()

            // then
            assertThat(result).hasSize(concerts.size)
            assertThat(result.first()).isInstanceOf(GetConcertsResult::class.java)
        }
    }

}