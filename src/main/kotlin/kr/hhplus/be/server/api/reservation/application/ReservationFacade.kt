package kr.hhplus.be.server.api.reservation.application

import kr.hhplus.be.server.api.reservation.application.dto.CreateReservationResult
import kr.hhplus.be.server.api.reservation.application.dto.PayReservationResult
import kr.hhplus.be.server.domain.balance.BalanceService
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.payment.model.event.PaymentCreateEvent
import kr.hhplus.be.server.domain.queue.ActiveQueueService
import kr.hhplus.be.server.domain.queue.TokenService
import kr.hhplus.be.server.domain.reservation.ReservationService
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.domain.support.component.lock.LockResource
import kr.hhplus.be.server.domain.support.component.lock.LockStrategy
import kr.hhplus.be.server.domain.support.component.lock.aop.DistributedLock
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class ReservationFacade(
    private val reservationService: ReservationService,
    private val concertService: ConcertService,
    private val paymentService: PaymentService,
    private val balanceService: BalanceService,
    private val activeQueueService: ActiveQueueService,
    private val tokenService: TokenService,
    private val eventPublisher: ApplicationEventPublisher,
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

        // 활성 큐에서 토큰 삭제
        activeQueueService.delete(token)

        // 발급 토큰 삭제
        tokenService.remove(reservation.userId)

        // 결제 생성
        val payment = paymentService.createPayment(reservation.userId, reservation.id, reservation.payAmount)

        // 이벤트 발행
        eventPublisher.publishEvent(PaymentCreateEvent.from(payment))
        return PayReservationResult.from(payment)
    }
}