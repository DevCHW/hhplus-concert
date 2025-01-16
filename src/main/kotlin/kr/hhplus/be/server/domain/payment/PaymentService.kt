package kr.hhplus.be.server.domain.payment

import kr.hhplus.be.server.domain.payment.model.CreatePayment
import kr.hhplus.be.server.domain.payment.model.Payment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {

    /**
     * 결제 생성
     */
    @Transactional
    fun createPayment(userId: String, reservationId: String, payAmount: BigDecimal): Payment {
        return paymentRepository.save(
            CreatePayment(
                userId = userId,
                reservationId = reservationId,
                amount = payAmount,
            )
        )
    }

}