package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock
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
        val balance = balanceRepository.getNullableByUserId(userId) ?: balanceRepository.create(userId)
        val modifyBalance = ModifyBalance(userId, balance.balance.plus(amount))
        return balanceRepository.modify(modifyBalance)
    }

    fun create(userId: String, amount: BigDecimal = BigDecimal.ZERO): Balance {
        return balanceRepository.create(userId, amount)
    }

    /**
     * 잔고 조회
     */
    fun getBalance(userId: String): Balance {
        return balanceRepository.getNullableByUserId(userId) ?: Balance.default(userId)
    }

    /**
     * 잔고 충전 잠금
     */
    fun chargeLock(userId: String): BalanceChargeLock {
        return balanceRepository.createChargeLock(userId)
    }

    /**
     * 잔고 충전 잠금 해제
     */
    fun chargeUnLock(chargeLockId: String) {
        balanceRepository.deleteChargeLock(chargeLockId)
    }

    /**
     * 잔액 차감
     */
    @Transactional
    fun decreaseBalance(userId: String, amount: BigDecimal): Balance {
        val balance = balanceRepository.getNullableByUserIdWithLock(userId) ?: balanceRepository.create(userId)
        val modifyBalance = ModifyBalance(userId, balance.balance.minus(amount))
        return balanceRepository.modify(modifyBalance)
    }

}