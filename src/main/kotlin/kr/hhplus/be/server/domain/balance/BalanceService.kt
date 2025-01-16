package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class BalanceService(
    private val balanceRepository: BalanceRepository,
) {

    /**
     * 잔고 충전
     */
    fun charge(userId: String, amount: BigDecimal): Balance {
        val lock = balanceRepository.chargeLock(userId)
        try {
            val balance = balanceRepository.getByUserId(userId)
            val modifyBalance = ModifyBalance(userId, balance.balance.plus(amount))
            return balanceRepository.modify(modifyBalance)
        } finally {
            balanceRepository.chargeUnLock(lock.id)
        }
    }

    /**
     * 잔고 조회
     */
    fun getBalance(userId: String): Balance {
        return balanceRepository.getByUserIdOrNull(userId) ?: Balance.default(userId)
    }

    /**
     * 잔액 차감
     */
    @Transactional
    fun decreaseBalance(userId: String, amount: BigDecimal): Balance {
        val balance = balanceRepository.getByUserIdWithLock(userId)
        val modifyBalance = ModifyBalance(userId, balance.balance.minus(amount))
        return balanceRepository.modify(modifyBalance)
    }

}