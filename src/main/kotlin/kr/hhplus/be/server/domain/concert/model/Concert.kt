package kr.hhplus.be.server.domain.concert.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Concert(
    val id: String,
    val title: String,
    val price: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    init {
        require(price >= BigDecimal.ZERO) {
            throw IllegalStateException("콘서트 가격은 0원 이상이어야 합니다.")
        }
    }
}
