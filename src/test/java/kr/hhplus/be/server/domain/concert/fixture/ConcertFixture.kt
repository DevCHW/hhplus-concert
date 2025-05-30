package kr.hhplus.be.server.domain.concert.fixture

import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.support.IdGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

class ConcertFixture {
    
    companion object {
        fun get(
            id: String = IdGenerator.generate(),
            title: String = "title",
            price: BigDecimal = BigDecimal.valueOf(100),
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Concert {
            return Concert(
                id = id,
                title = title,
                price = price,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}