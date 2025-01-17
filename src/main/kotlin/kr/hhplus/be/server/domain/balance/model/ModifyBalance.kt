package kr.hhplus.be.server.domain.balance.model

import java.math.BigDecimal

data class ModifyBalance(
    val userId: String,
    val balance: BigDecimal,
)