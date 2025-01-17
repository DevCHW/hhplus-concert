package kr.hhplus.be.server.domain.balance.model

import java.math.BigDecimal

data class CreateBalance(
    val userId: String,
    val balance: BigDecimal,
)
