package kr.hhplus.be.server.domain.balance.fixture

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.support.IdGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

class BalanceFixture {

    companion object {
        fun get(
            userId: String = IdGenerator.generate(),
            balance: BigDecimal = BigDecimal.ZERO,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Balance {
            return Balance(
                userId = userId,
                balance = balance,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }

}