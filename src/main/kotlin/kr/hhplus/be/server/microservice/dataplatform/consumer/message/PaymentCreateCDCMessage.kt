package kr.hhplus.be.server.microservice.dataplatform.consumer.message

import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentCreateCDCMessage (
    val userId: String,
    val reservationId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)