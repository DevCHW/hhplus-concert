package kr.hhplus.be.server.api.balance.controller.dto.response

import java.math.BigDecimal

data class ChargeResponse(
    val balance: BigDecimal,
)
