package kr.hhplus.be.server.domain.reservation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hhplus.be.server.domain.reservation.error.SeatAlreadyReservedException
import kr.hhplus.be.server.domain.reservation.fixture.ReservationFixture
import kr.hhplus.be.server.domain.reservation.model.Reservation
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@DisplayName("예약 서비스 단위 테스트")
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

    @Nested
    inner class `대기 시간 초과 예약 취소` {
        @Test
        fun `PENDING 상태의 예약 목록을 조회하여 시간이 초과된 예약을 취소 처리한다`() {
            // given
            val timeoutDuration = 60L
            val now = LocalDateTime.now()
            val reservation1 = ReservationFixture.createReservation(
                status = Reservation.Status.PENDING,
                updatedAt = now.minusSeconds(timeoutDuration + 1)
            )
            val reservation2 = ReservationFixture.createReservation(
                status = Reservation.Status.PENDING,
                updatedAt = now.minusSeconds(timeoutDuration + 1)
            )

            every { reservationRepository.getByStatus(Reservation.Status.PENDING) }
                .returns(listOf(reservation1, reservation2))

            every { reservationRepository.modifyStatusByIdsIn(Reservation.Status.CANCEL, any()) }
                .returns(2)

            // when
            reservationService.cancelTimeoutReservations(timeoutDuration, now)

            // then
            verify(exactly = 1) { reservationRepository.modifyStatusByIdsIn(Reservation.Status.CANCEL, any()) }
        }

        @Test
        fun `PENDING 상태의 예약 목록이 없는 경우 아무 작업도 수행하지 않는다`() {
            // given
            val timeoutDuration = 60L
            val now = LocalDateTime.now()

            every { reservationRepository.getByStatus(Reservation.Status.PENDING) }
                .returns(emptyList())

            // when
            reservationService.cancelTimeoutReservations(timeoutDuration, now)

            // then
            verify(exactly = 0) { reservationRepository.modifyStatusByIdsIn(any(), any()) }
        }
    }
}