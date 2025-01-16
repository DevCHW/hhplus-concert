package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceChargeLockEntity
import java.math.BigDecimal

interface BalanceRepository {
    fun getByUserIdOrNull(userId: String): Balance?
    fun create(userId: String, balance: BigDecimal = BigDecimal.ZERO): Balance
    fun getByUserIdWithLock(userId: String): Balance
    fun chargeLock(userId: String): BalanceChargeLockEntity
    fun chargeUnLock(chargeLockId: String)
    fun getByUserId(userId: String): Balance
    fun modify(modifyBalance: ModifyBalance): Balance
}