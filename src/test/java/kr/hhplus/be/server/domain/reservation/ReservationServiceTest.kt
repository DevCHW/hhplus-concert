package kr.hhplus.be.server.domain.reservation

import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.reservation.error.SeatAlreadyReservedException
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ReservationServiceTest {
    private lateinit var reservationService: ReservationService
    private lateinit var reservationRepository: ReservationRepository

    @BeforeEach
    fun setUp() {
        reservationRepository = mockk()
        reservationService = ReservationService(reservationRepository)
    }

    @Nested
    inner class `예약 생성` {
        @Test
        fun `좌석에 해당하는 예약이 존재하지 않는 경우 예약을 생성하고 반환한다`() {
            // given
            val createReservation = ReservationFixture.createCreateReservation()
            val reservation = ReservationFixture.createReservation(
                seatId = createReservation.seatId,
                userId = createReservation.userId,
                status = createReservation.status,
            )

            every { reservationRepository.isExistBySeatId(createReservation.seatId) } returns false
            every { reservationRepository.save(any()) } returns reservation

            // when
            val result = reservationService.createReservation(createReservation)

            // then
            assertThat(result.seatId).isEqualTo(reservation.seatId)
            assertThat(result.userId).isEqualTo(reservation.userId)
            assertThat(result.status).isEqualTo(reservation.status)
        }

        @Test
        fun `좌석에 해당하는 예약이 이미 존재하는 경우 SeatAlreadyReservedException 예외가 발생한다`() {
            // given
            val createReservation = ReservationFixture.createCreateReservation()

            every { reservationRepository.isExistBySeatId(createReservation.seatId) } returns true

            // when & then
            assertThatThrownBy {
                reservationService.createReservation(createReservation)
            }
                .isInstanceOf(SeatAlreadyReservedException::class.java)
                .hasMessage("이미 예약된 좌석입니다.")
        }
    }
}