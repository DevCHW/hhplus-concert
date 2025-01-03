package kr.hhplus.be.server.api.balance.controller.dto.request

import java.math.BigDecimal

data class ChargeRequest(
    val userId: String,
    val amount: BigDecimal,
)
