package kr.hhplus.be.server.api.payment.application

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.balance.fixture.BalanceFixture
import kr.hhplus.be.server.domain.payment.PaymentRepository
import kr.hhplus.be.server.domain.reservation.ReservationRepository
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.token.TokenRepository
import kr.hhplus.be.server.domain.token.fixture.TokenFixture
import kr.hhplus.be.server.domain.token.model.Token
import kr.hhplus.be.server.support.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("PaymentFacade 통합 테스트")
class PaymentFacadeIT(
    private val paymentFacade: PaymentFacade,
    private val paymentRepository: PaymentRepository,
    private val balanceRepository: BalanceRepository,
    private val reservationRepository: ReservationRepository,
    private val tokenRepository: TokenRepository,
) : IntegrationTestSupport() {


    @Nested
    inner class `예약 결제` {
        // given
        val balance = balanceRepository.save(BalanceFixture.createBalance(
            userId = TsidCreator.getTsid().toString(),
            balance = BigDecimal.valueOf(100)
        ))

        val reservation = reservationRepository.save(ReservationFixture.createReservation(
            userId = balance.userId,
            payAmount = BigDecimal.valueOf(100)
        ))

        val token = tokenRepository.save(TokenFixture.createToken(
            userId = balance.userId,
            status = Token.Status.ACTIVE,
        ))

        // when
        val result = paymentFacade.createPayment(reservation.id, token.token)

        @Test
        fun `성공 시 결제 내역이 남는다`() {
            // when
            val payment = paymentRepository.getById(result.paymentId)

            // then
            assertThat(payment).isNotNull
        }

        @Test
        fun `성공 시 예약의 결제 금액만큼 잔고가 차감된다`() {
            // when
            val result = balanceRepository.getByUserId(balance.userId)

            // then
            assertThat(result.balance).isEqualTo(balance.balance.minus(reservation.payAmount))
        }

        @Test
        fun `성공 시 대기열 토큰은 삭제된다`() {
            // when
            assertThatThrownBy { tokenRepository.getByToken(token.token) }
                .isInstanceOf(Exception::class.java)
        }

        @Test
        fun `성공 시 예약 상태는 COMPLETED 상태가 된다`() {
            // when
            val reservation = reservationRepository.getById(reservation.id)

            // then
            assertThat(reservation.status).isEqualTo(Reservation.Status.COMPLETED)
        }
    }
}