package kr.hhplus.be.server.domain.concert.fixture

import kr.hhplus.be.server.domain.concert.model.Concert
import java.math.BigDecimal

class ConcertFixture {

    companion object {
        fun createConcert(
            title: String = "title",
            price: BigDecimal = BigDecimal.valueOf(100),
        ): Concert {
            return Concert(
                title = title,
                price = price,
            )
        }
    }
}