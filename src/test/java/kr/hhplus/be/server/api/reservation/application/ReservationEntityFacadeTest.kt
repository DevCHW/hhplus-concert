package kr.hhplus.be.server.api.reservation.application

import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("예약 Facade 단위 테스트")
class ReservationEntityFacadeTest {
    private lateinit var reservationFacade: ReservationFacade
    private lateinit var reservationService: ReservationService
    private lateinit var concertService: ConcertService

    @BeforeEach
    fun setUp() {
        reservationService = mockk()
        concertService = mockk()
        reservationFacade = ReservationFacade(
            concertService = concertService,
            reservationService = reservationService,
        )
    }

    @Nested
    inner class `예약 생성` {
        @Test
        fun `필요한 정보를 받아 예약을 생성하고 예약 생성 결과를 반환한다`() {
            // given
            val concert = ConcertFixture.createConcert()
            val createReservation = ReservationFixture.createCreateReservation(payAmount = concert.price)
            val reservation = ReservationFixture.createReservation(
                seatId = createReservation.seatId,
                userId = createReservation.userId,
                payAmount = createReservation.payAmount,
            )

            every { concertService.getConcert(concert.id) } returns concert
            every { reservationService.createReservation(createReservation) } returns reservation

            // when
            val result =
                reservationFacade.createReservation(concert.id, reservation.userId, reservation.seatId)

            // then
            assertThat(result.id).isEqualTo(reservation.id)
            assertThat(result.status).isEqualTo(reservation.status.name)
            assertThat(result.seatId).isEqualTo(reservation.seatId)
        }
    }

}