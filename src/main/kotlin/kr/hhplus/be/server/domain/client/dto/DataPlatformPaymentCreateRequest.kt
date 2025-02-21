package kr.hhplus.be.server.domain.client.dto

import java.math.BigDecimal

class DataPlatformPaymentCreateRequest(
    val userId: String,
    val reservationId: String,
    val amount: BigDecimal,
)