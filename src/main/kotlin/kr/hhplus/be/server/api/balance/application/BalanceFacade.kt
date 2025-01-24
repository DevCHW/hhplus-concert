package kr.hhplus.be.server.api.balance.application

import kr.hhplus.be.server.api.balance.application.dto.ChargeBalanceResult
import kr.hhplus.be.server.api.balance.application.dto.GetBalanceResult
import kr.hhplus.be.server.domain.balance.BalanceService
import kr.hhplus.be.server.domain.support.lock.DistributedLockStrategy
import kr.hhplus.be.server.domain.support.lock.aop.DistributedLock
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class BalanceFacade(
    private val balanceService: BalanceService,
) {

    /**
     * 충전
     */
    @DistributedLock(lockName = "#userId", strategy = DistributedLockStrategy.REDIS_SPIN_LOCK, waitTime = 0)
    fun charge(userId: String, amount: BigDecimal): ChargeBalanceResult {
        val balance = balanceService.charge(userId, amount)
        return ChargeBalanceResult.from(balance)
    }

    /**
     * 조회
     */
    fun getBalance(userId: String): GetBalanceResult {
        val balance = balanceService.getBalance(userId)
        return GetBalanceResult.from(balance)
    }

}