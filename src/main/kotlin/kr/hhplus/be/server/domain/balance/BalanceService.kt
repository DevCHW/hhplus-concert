package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.domain.support.lock.LockStrategy
import kr.hhplus.be.server.domain.support.lock.LockResource
import kr.hhplus.be.server.domain.support.lock.aop.DistributedLock
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
    @DistributedLock(resource = LockResource.BALANCE, id = "#userId", strategy = LockStrategy.REDIS_SPIN_LOCK)
    fun charge(userId: String, amount: BigDecimal): Balance {
        val balance = balanceRepository.getNullableByUserId(userId) ?: balanceRepository.create(userId)

        return balanceRepository.modify(
            ModifyBalance(
                userId = userId,
                balance = balance.balance + amount
            )
        )
    }

    /**
     * 잔액 차감
     */
    @Transactional
    @DistributedLock(resource = LockResource.BALANCE, id = "#userId", strategy = LockStrategy.REDIS_PUB_SUB)
    fun decreaseBalance(userId: String, amount: BigDecimal): Balance {
        val balance = balanceRepository.getNullableByUserId(userId) ?: balanceRepository.create(userId)

        return balanceRepository.modify(
            ModifyBalance(
                userId = userId,
                balance = balance.balance - amount
            )
        )
    }

    /**
     * 잔고 조회
     */
    fun getBalance(userId: String): Balance {
        return balanceRepository.getNullableByUserId(userId) ?: Balance.default(userId)
    }
}