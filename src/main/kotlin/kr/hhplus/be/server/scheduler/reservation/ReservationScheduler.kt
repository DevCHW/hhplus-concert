package kr.hhplus.be.server.scheduler.reservation

import kr.hhplus.be.server.domain.reservation.ReservationService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReservationScheduler(
    private val reservationService: ReservationService,
) {

    /**
     * 대기시간 초과 예약 취소 처리
     */
//    @Scheduled(cron = "0 0/5 * * * ?") // 매 5분마다 실행
    fun cancelTimeoutReservations() {
        val now = LocalDateTime.now()
        reservationService.cancelTimeoutReservations(PENDING_TIMEOUT, now)
    }

    companion object {
        private const val PENDING_TIMEOUT = 3 * 60L // 5분
    }
}
