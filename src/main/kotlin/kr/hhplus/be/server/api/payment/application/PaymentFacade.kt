package kr.hhplus.be.server.api.payment.application

import kr.hhplus.be.server.api.payment.application.dto.CreatePaymentResult
import kr.hhplus.be.server.domain.balance.BalanceService
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.token.TokenService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class PaymentFacade(
    private val paymentService: PaymentService,
    private val balanceService: BalanceService,
    private val reservationService: ReservationService,
    private val tokenService: TokenService,
) {

    /**
     * 결제 생성
     */
    @Transactional
    fun createPayment(reservationId: String, token: UUID): CreatePaymentResult {
        // 예약 조회
        val reservation = reservationService.getReservation(reservationId)

        // 잔액 차감
        balanceService.decreaseBalance(reservation.userId, reservation.payAmount)

        // 예약 상태 변경
        reservationService.modifyStatus(reservationId, Reservation.Status.COMPLETED)

        // 토큰 삭제
        tokenService.deleteToken(token)

        // 결제 기록
        val payment = paymentService.createPayment(reservation.userId, reservation.id, reservation.payAmount)

        return CreatePaymentResult.from(payment)
    }
}