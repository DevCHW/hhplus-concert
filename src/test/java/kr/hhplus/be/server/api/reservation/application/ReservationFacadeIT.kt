package kr.hhplus.be.server.api.reservation.application

import io.hhplus.cleanarchitecture.support.concurrent.ConcurrencyTestUtils
import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository
import kr.hhplus.be.server.domain.concert.repository.SeatRepository
import kr.hhplus.be.server.domain.concert.model.CreateConcert
import kr.hhplus.be.server.domain.concert.model.CreateSeat
import kr.hhplus.be.server.domain.payment.PaymentRepository
import kr.hhplus.be.server.domain.queue.repository.ActiveQueueRedisRepository
import kr.hhplus.be.server.domain.queue.repository.TokenRedisRepository
import kr.hhplus.be.server.domain.reservation.ReservationRepository
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.support.IdGenerator
import kr.hhplus.be.server.support.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@DisplayName("예약 Facade 통합 테스트")
class ReservationFacadeIT(
    private val reservationFacade: ReservationFacade,
    private val reservationRepository: ReservationRepository,
    private val concertRepository: ConcertRepository,
    private val seatRepository: SeatRepository,
    private val paymentRepository: PaymentRepository,
    private val balanceRepository: BalanceRepository,
    private val tokenRedisRepository: TokenRedisRepository,
    private val activeQueueRepository: ActiveQueueRedisRepository,
) : IntegrationTestSupport() {

    @Nested
    inner class `좌석 예약` {
        // given
        val userId = IdGenerator.generate()
        val concert = concertRepository.save(
            CreateConcert(
                title = "title",
                price = BigDecimal(100)
            )
        )

        val seat = seatRepository.save(
            CreateSeat(
                concertScheduleId = IdGenerator.generate(),
                number = 1
            )
        )

        // when
        val result = reservationFacade.createReservation(concert.id, userId, seat.id)

        @Test
        fun `성공 시 예약이 생성된다`() {
            // then
            val reservation = reservationRepository.getById(result.id)
            assertThat(reservation).isNotNull
        }

        @Test
        fun `예약의 결제 금액은 콘서트의 가격과 같다`() {
            // then
            val reservation = reservationRepository.getById(result.id)
            assertThat(reservation.payAmount).isEqualTo(concert.price)
        }

        @Test
        fun `예약의 상태는 PENDING 상태여야 한다`() {
            // then
            val reservation = reservationRepository.getById(result.id)
            assertThat(reservation.status).isEqualTo(Reservation.Status.PENDING)
        }

        @Test
        fun `동일한 좌석에 10번 요청이 동시에 들어오더라도 1번만 성공해야 한다`() {
            // given
            val userId = IdGenerator.generate()
            val concert = concertRepository.save(
                CreateConcert(
                    title = "title",
                    price = BigDecimal.valueOf(100)
                )
            )

            val seat = seatRepository.save(
                CreateSeat(
                    concertScheduleId = IdGenerator.generate(),
                    number = 1,
                )
            )

            val successCount = AtomicInteger()
            val failCount = AtomicInteger()
            val action = Runnable {
                try {
                    reservationFacade.createReservation(concert.id, userId, seat.id)
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            }

            // when
            ConcurrencyTestUtils.executeConcurrently(10, action)

            // then
            assertThat(successCount.get()).isEqualTo(1)
            assertThat(failCount.get()).isEqualTo(9)
        }
    }


    @Nested
    inner class `예약 결제` {
        // given
        val userId = IdGenerator.generate()
        val seatId = IdGenerator.generate()
        val payAmount = BigDecimal.valueOf(100)

        val balance = balanceRepository.create(userId, BigDecimal(100))

        val reservation = reservationRepository.save(
            CreateReservation(
                userId = userId,
                seatId = seatId,
                payAmount = payAmount,
            )
        )

        val token = tokenRedisRepository.createToken(userId) ?: throw RuntimeException("토큰 저장 실패")
        val success = activeQueueRepository.addTokens(mutableSetOf(token))

        @Test
        fun `성공 시 결제 내역이 남아야 한다`() {
            // when
            val result = reservationFacade.payReservation(reservation.id, UUID.randomUUID())
            val payment = paymentRepository.getById(result.paymentId)

            // then
            assertThat(payment).isNotNull
        }

        @Test
        fun `성공 시 예약의 결제 금액만큼 잔고가 차감된다`() {
            // when
            reservationFacade.payReservation(reservation.id, token)
            val result = balanceRepository.getByUserId(balance.userId)

            // then
            assertThat(result.balance).isEqualTo(balance.balance.minus(reservation.payAmount))
        }

        @Test
        fun `성공 시 대기열 토큰은 삭제된다`() {
            // when
            reservationFacade.payReservation(reservation.id, token)
            val result = tokenRedisRepository.getNullableToken(reservation.userId)

            // then
            assertThat(result).isNull()
        }

        @Test
        fun `성공 시 예약 상태는 COMPLETED 상태가 된다`() {
            // when
            reservationFacade.payReservation(reservation.id, token)
            val reservation = reservationRepository.getById(reservation.id)

            // then
            assertThat(reservation.status).isEqualTo(Reservation.Status.COMPLETED)
        }

        @Test
        fun `같은 예약에 대해 동시에 결제 요청이 5번 들어오더라도 1번만 성공해야 한다`() {
            // given
            val successCount = AtomicInteger()
            val failCount = AtomicInteger()
            val action = Runnable {
                try {
                    reservationFacade.payReservation(reservation.id, token)
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            }

            // when
            ConcurrencyTestUtils.executeConcurrently(5, action)

            // then
            assertThat(successCount.get()).isEqualTo(1)
            assertThat(failCount.get()).isEqualTo(4)
        }
    }
}