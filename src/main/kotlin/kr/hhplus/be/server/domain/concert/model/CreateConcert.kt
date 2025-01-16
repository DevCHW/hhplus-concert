package kr.hhplus.be.server.domain.concert.model

import java.math.BigDecimal

data class CreateConcert(
    val title: String,
    val price: BigDecimal,
)
