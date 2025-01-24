package kr.hhplus.be.server.domain.balance.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.balance.model.Balance
import java.math.BigDecimal
import java.time.LocalDateTime

class BalanceFixture {

    companion object {
        fun get(
            userId: String = TsidCreator.getTsid().toString(),
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