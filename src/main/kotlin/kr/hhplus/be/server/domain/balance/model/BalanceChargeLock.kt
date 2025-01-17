package kr.hhplus.be.server.domain.balance.model

import java.time.LocalDateTime

data class BalanceChargeLock(
    val id: String,
    val userId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
