package kr.hhplus.be.server.api.reservation.application

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.cleanarchitecture.support.concurrent.ConcurrencyTestUtils
import kr.hhplus.be.server.domain.concert.ConcertRepository
import kr.hhplus.be.server.domain.concert.SeatRepository
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.domain.concert.fixture.SeatFixture
import kr.hhplus.be.server.domain.reservation.ReservationRepository
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.support.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicInteger

@DisplayName("예약 Facade 통합 테스트")
class ReservationFacadeIT(
    private val reservationFacade: ReservationFacade,
    private val reservationRepository: ReservationRepository,
    private val concertRepository: ConcertRepository,
    private val seatRepository: SeatRepository,
) : IntegrationTestSupport() {

    @Nested
    inner class `좌석 예약` {
        // given
        val userId = TsidCreator.getTsid().toString()
        val concert = concertRepository.save(
            ConcertFixture.createConcert(
                price = BigDecimal.valueOf(100)
            )
        )

        val seat = seatRepository.save(
            SeatFixture.createSeat()
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
            val userId = TsidCreator.getTsid().toString()
            val concert = concertRepository.save(
                ConcertFixture.createConcert(
                    price = BigDecimal.valueOf(100)
                )
            )

            val seat = seatRepository.save(
                SeatFixture.createSeat(number = 2)
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
}