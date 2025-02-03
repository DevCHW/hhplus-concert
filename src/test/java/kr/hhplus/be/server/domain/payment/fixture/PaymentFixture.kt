package kr.hhplus.be.server.domain.payment.fixture

import kr.hhplus.be.server.domain.payment.model.Payment
import kr.hhplus.be.server.support.IdGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentFixture {
    companion object {
        fun get(
            id: String = IdGenerator.generate(),
            userId: String = IdGenerator.generate(),
            reservationId: String = IdGenerator.generate(),
            amount: BigDecimal = BigDecimal(100),
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Payment {
            return Payment(
                id = id,
                userId = userId,
                reservationId = reservationId,
                amount = amount,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}