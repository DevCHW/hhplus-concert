package kr.hhplus.be.server.domain.balance.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Balance(
    val userId: String,
    val balance: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun default(userId: String): Balance {
            return Balance(
                userId = userId,
                balance = BigDecimal.ZERO,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        }
    }
}
