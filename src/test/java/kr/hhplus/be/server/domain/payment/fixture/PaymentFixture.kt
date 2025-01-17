package kr.hhplus.be.server.domain.payment.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.payment.model.Payment
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentFixture {
    companion object {
        fun createPayment(
            id: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
            reservationId: String = TsidCreator.getTsid().toString(),
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