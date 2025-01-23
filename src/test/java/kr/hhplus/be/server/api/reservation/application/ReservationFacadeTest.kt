package kr.hhplus.be.server.api.reservation.application

import com.github.f4b6a3.tsid.TsidCreator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hhplus.be.server.api.reservation.application.dto.PayReservationResult
import kr.hhplus.be.server.domain.balance.BalanceService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.payment.fixture.PaymentFixture
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.fixture.CreateReservationFixture
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import kr.hhplus.be.server.domain.token.TokenService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
            val concert = ConcertFixture.get()
            val createReservation = CreateReservationFixture.get(payAmount = concert.price)
            val reservation = ReservationFixture.get(
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

        @Test
        fun `좌석에 해당하는 예약이 이미 존재하는 경우 CoreException 예외가 발생한다`() {
            // given
            val concertId = TsidCreator.getTsid().toString()
            val seatId = TsidCreator.getTsid().toString()
            val userId = TsidCreator.getTsid().toString()

            every { reservationService.isExistBySeatId(seatId) } returns true

            // when & then
            assertThatThrownBy {
                reservationFacade.createReservation(concertId, userId, seatId)
            }
                .isInstanceOf(CoreException::class.java)
                .hasMessage("이미 예약된 좌석입니다.")
                .extracting("errorType")
                .isEqualTo(ErrorType.ALREADY_RESERVED_SEAT)
        }
    }

    @Nested
    inner class `예약 결제` {
        @Test
        fun `결제 성공 시 결제 성공 결과를 반환한다`() {
            // given
            val token = UUID.randomUUID()
            val reservation = ReservationFixture.get(status = Reservation.Status.PENDING)
            val payment = PaymentFixture.get()

            every { reservationService.getReservationWithLock(any()) } returns reservation
            every { paymentService.createPayment(any(), any(), any()) } returns payment

            // when
            val result = reservationFacade.payReservation(reservation.id, token)

            // then
            assertThat(result).isInstanceOf(PayReservationResult::class.java)
        }

        @Test
        fun `관련 서비스들이 올바르게 호출되는지 검증한다`() {
            // given
            val token = UUID.randomUUID()
            val reservation = ReservationFixture.get()

            // mock
            every { reservationService.getReservationWithLock(any()) } returns reservation

            // when
            reservationFacade.payReservation(reservation.id, token)

            // then
            // 잔액 차감 호출
            verify(exactly = 1) {
                balanceService.decreaseBalance(reservation.userId, reservation.payAmount)
            }

            // 결제 내역 생성 호출
            verify(exactly = 1) {
                paymentService.createPayment(reservation.userId, reservation.id, reservation.payAmount)
            }

            // 예약 수정 호출
            verify(exactly = 1) {
                reservationService.modifyReservation(reservation.id, Reservation.Status.COMPLETED)
            }

            // 토큰 삭제 호출
            verify(exactly = 1) {
                tokenService.deleteToken(token)
            }
        }

        @Test
        fun `예약이 결제가 가능한 상태가 아니라면 CoreException 예외가 발생한다`() {
            // given
            val cancelReservation = ReservationFixture.get(status = Reservation.Status.CANCEL)
            val token = UUID.randomUUID()
            every { reservationService.getReservationWithLock(any()) } returns cancelReservation

            // when & then
            assertThatThrownBy {
                reservationFacade.payReservation(cancelReservation.id, token)
            }
                .isInstanceOf(CoreException::class.java)
                .extracting("errorType")
                .isEqualTo(ErrorType.NOT_PAYABLE_STATE)
        }
    }

}