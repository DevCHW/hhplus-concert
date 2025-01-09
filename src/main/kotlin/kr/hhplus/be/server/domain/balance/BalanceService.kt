package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BalanceService(
    private val balanceRepository: BalanceRepository,
) {

    /**
     * 잔고 충전
     */
    fun charge(userId: String, amount: BigDecimal): Balance {
        val chargedBalance = balanceRepository.getByUserIdOrNull(userId)?.charge(amount)
            ?: Balance(userId = userId, balance = amount)

        return balanceRepository.save(chargedBalance)
    }

    /**
     * 잔고 조회
     */
    fun getBalance(userId: String): Balance {
        return balanceRepository.getByUserIdOrNull(userId)
            ?: Balance(userId = userId, balance = BigDecimal.ZERO)
    }

}