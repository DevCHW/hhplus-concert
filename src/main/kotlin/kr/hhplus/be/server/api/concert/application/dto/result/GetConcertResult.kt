package kr.hhplus.be.server.api.concert.application.dto.result

import kr.hhplus.be.server.domain.concert.model.Concert
import java.math.BigDecimal
import java.time.LocalDateTime

data class GetConcertResult(
    val id: String,
    val title: String,
    val price: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(concert: Concert): GetConcertResult {
            return GetConcertResult(
                id = concert.id,
                title = concert.title,
                price = concert.price,
                createdAt = concert.createdAt,
                updatedAt = concert.updatedAt,
            )
        }
    }
}