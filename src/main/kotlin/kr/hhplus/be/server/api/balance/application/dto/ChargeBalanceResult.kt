package kr.hhplus.be.server.api.balance.application.dto

import kr.hhplus.be.server.domain.balance.model.Balance
import java.math.BigDecimal
import java.time.LocalDateTime

data class ChargeBalanceResult(
    val userId: String,
    val balance: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(balance: Balance): ChargeBalanceResult {
            return ChargeBalanceResult(
                userId = balance.userId,
                balance = balance.balance,
                createdAt = balance.createdAt,
                updatedAt = balance.updatedAt
            )
        }
    }
}
