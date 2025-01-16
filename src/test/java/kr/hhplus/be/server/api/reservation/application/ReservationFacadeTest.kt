package kr.hhplus.be.server.api.reservation.application

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hhplus.be.server.api.reservation.application.dto.CreatePaymentResult
import kr.hhplus.be.server.domain.balance.BalanceService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.token.TokenService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("예약 Facade 단위 테스트")
class ReservationFacadeTest {
    private lateinit var reservationFacade: ReservationFacade
    private lateinit var reservationService: ReservationService
    private lateinit var concertService: ConcertService
    private lateinit var paymentService: PaymentService
    private lateinit var balanceService: BalanceService
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setUp() {
        reservationService = mockk(relaxed = true)
        concertService = mockk(relaxed = true)
        paymentService = mockk(relaxed = true)
        balanceService = mockk(relaxed = true)
        tokenService = mockk(relaxed = true)
        reservationFacade = ReservationFacade(
            reservationService = reservationService,
            concertService = concertService,
            paymentService = paymentService,
            balanceService = balanceService,
            tokenService = tokenService,
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

    @Nested
    inner class `결제` {

        @Test
        fun `결제 성공 시 결제 성공 결과를 반환한다`() {
            // given
            val token = UUID.randomUUID()
            val reservation = ReservationFixture.createReservation()

            // when
            val result = reservationFacade.createPayment(reservation.id, token)

            // then
            assertThat(result).isInstanceOf(CreatePaymentResult::class.java)
        }

        @Test
        fun `관련 서비스들이 올바르게 호출되는지 검증한다`() {
            // given
            val token = UUID.randomUUID()
            val reservation = ReservationFixture.createReservation()

            // mock
            every { reservationService.getReservation(any()) } returns reservation

            // when
            reservationFacade.createPayment(reservation.id, token)

            // then
            verify(exactly = 1) {
                balanceService.decreaseBalance(reservation.userId, reservation.payAmount)
            }

            verify(exactly = 1) {
                paymentService.createPayment(reservation.userId, reservation.id, reservation.payAmount)
            }

            verify(exactly = 1) {
                reservationService.modifyReservation(reservation.id, Reservation.Status.COMPLETED)
            }

            verify(exactly = 1) {
                tokenService.deleteToken(token)
            }
        }
    }

}