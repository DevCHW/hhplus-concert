package kr.hhplus.be.server.domain.concert.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Concert(
    val id: String,
    val title: String,
    val price: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
