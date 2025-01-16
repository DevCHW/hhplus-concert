package kr.hhplus.be.server.domain.payment.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Payment(
    val id: String,
    val userId: String,
    val reservationId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
