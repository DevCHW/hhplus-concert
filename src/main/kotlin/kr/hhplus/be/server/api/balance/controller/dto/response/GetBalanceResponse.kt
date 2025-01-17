package kr.hhplus.be.server.api.balance.controller.dto.response

import java.math.BigDecimal

data class GetBalanceResponse(
    val balance: BigDecimal,
)
