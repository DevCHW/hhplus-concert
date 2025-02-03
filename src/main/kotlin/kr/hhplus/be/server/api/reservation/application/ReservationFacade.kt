package kr.hhplus.be.server.api.reservation.application

import kr.hhplus.be.server.api.reservation.application.dto.CreateReservationResult
import kr.hhplus.be.server.api.reservation.application.dto.PayReservationResult
import kr.hhplus.be.server.domain.balance.BalanceService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.queue.QueueService
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import kr.hhplus.be.server.domain.support.lock.LockResource
import kr.hhplus.be.server.domain.support.lock.LockStrategy
import kr.hhplus.be.server.domain.support.lock.aop.DistributedLock
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class ReservationFacade(
    private val reservationService: ReservationService,
    private val concertService: ConcertService,
    private val paymentService: PaymentService,
    private val balanceService: BalanceService,
    private val tokenService: QueueService,
) {

    /**
     * 예약 생성
     */
    @DistributedLock(resource = LockResource.SEAT, key = "#seatId", strategy = LockStrategy.REDIS_PUB_SUB)
    fun createReservation(
        concertId: String,
        userId: String,
        seatId: String,
    ): CreateReservationResult {
        // 콘서트 조회
        val concert = concertService.getConcert(concertId)

        // 좌석 예약 존재 여부 조회
        val exist = reservationService.isExistBySeatId(seatId)
        if (exist) {
            throw CoreException(ErrorType.ALREADY_RESERVED_SEAT)
        }

        // 예약 생성
        val reservation = reservationService.createReservation(
            CreateReservation(
                userId = userId,
                seatId = seatId,
                payAmount = concert.price
            )
        )

        return CreateReservationResult.from(reservation)
    }

    /**
     * 예약 결제
     */
    @Transactional
    @DistributedLock(resource = LockResource.RESERVATION, key = "#reservationId", strategy = LockStrategy.REDIS_PUB_SUB)
    fun payReservation(reservationId: String, token: UUID): PayReservationResult {
        // 예약 조회
        val reservation = reservationService.getReservation(reservationId)

        // 결제 대기 상태가 아니라면 예외 발생
        if (reservation.status != Reservation.Status.PENDING) {
            throw CoreException(ErrorType.NOT_PAYABLE_STATE)
        }

        // 잔액 차감
        balanceService.decreaseBalance(reservation.userId, reservation.payAmount)

        // 예약 상태 변경
        reservationService.modifyReservation(reservationId, Reservation.Status.COMPLETED)

        // 토큰 삭제
        tokenService.deleteToken(token)

        // 결제 생성
        val payment = paymentService.createPayment(reservation.userId, reservation.id, reservation.payAmount)
        return PayReservationResult.from(payment)
    }
}