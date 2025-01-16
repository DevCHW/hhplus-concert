package kr.hhplus.be.server.scheduler.reservation

import io.mockk.*
import kr.hhplus.be.server.domain.reservation.ReservationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("예약 스케줄러 단위 테스트")
class ReservationEntitySchedulerTest {
    private lateinit var reservationService: ReservationService
    private lateinit var reservationScheduler: ReservationScheduler

    @BeforeEach
    fun setUp() {
        reservationService = mockk()
        reservationScheduler = ReservationScheduler(reservationService)
    }

    @Nested
    inner class `대기시간 초과 예약 취소` {
        @Test
        fun `예약 서비스의 cancelTimeoutReservations 메서드가 호출된다`() {
            // given
            every { reservationService.cancelTimeoutReservations(any(), any()) } just Runs

            // when
            reservationScheduler.cancelTimeoutReservations()

            // then
            verify(exactly = 1) {
                reservationService.cancelTimeoutReservations(any(), any())
            }
        }
    }
}