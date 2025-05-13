package kr.hhplus.be.server.domain.payment.model

import java.math.BigDecimal
import java.time.LocalDateTime

class Payment(
    val id: String,
    val userId: String,
    val reservationId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
}


