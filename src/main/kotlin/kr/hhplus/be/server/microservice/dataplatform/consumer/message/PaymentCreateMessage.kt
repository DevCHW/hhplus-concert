package kr.hhplus.be.server.microservice.dataplatform.consumer.message

import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentCreateMessage (
    val userId: String,
    val idempotencyKey: String,
    val reservationId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)