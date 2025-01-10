package kr.hhplus.be.server.api.payment.application

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hhplus.be.server.api.payment.application.dto.CreatePaymentResult
import kr.hhplus.be.server.domain.balance.BalanceService
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

@DisplayName("결제 Facade 단위 테스트")
class PaymentFacadeTest {

    private lateinit var paymentService: PaymentService
    private lateinit var balanceService: BalanceService
    private lateinit var reservationService: ReservationService
    private lateinit var tokenService: TokenService
    private lateinit var paymentFacade: PaymentFacade

    @BeforeEach
    fun setUp() {
        paymentService = mockk(relaxed = true)
        tokenService = mockk(relaxed = true)
        balanceService = mockk(relaxed = true)
        reservationService = mockk(relaxed = true)
        paymentFacade = PaymentFacade(
            paymentService = paymentService,
            balanceService = balanceService,
            reservationService = reservationService,
            tokenService = tokenService,
        )
    }

    @Nested
    inner class `결제` {

        @Test
        fun `결제 성공 시 결제 성공 결과를 반환한다`() {
            // given
            val token = UUID.randomUUID()
            val reservation = ReservationFixture.createReservation()

            // when
            val result = paymentFacade.createPayment(reservation.id, token)

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
            paymentFacade.createPayment(reservation.id, token)

            // then
            verify(exactly = 1) {
                balanceService.decreaseBalance(reservation.userId, reservation.payAmount)
            }

            verify(exactly = 1) {
                paymentService.createPayment(reservation.userId, reservation.id, reservation.payAmount)
            }

            verify(exactly = 1) {
                reservationService.modifyStatus(reservation.id, Reservation.Status.COMPLETED)
            }

            verify(exactly = 1) {
                tokenService.deleteToken(token)
            }
        }
    }


}