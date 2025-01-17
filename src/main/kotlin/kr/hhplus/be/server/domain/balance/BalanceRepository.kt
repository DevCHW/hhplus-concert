package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import java.math.BigDecimal

interface BalanceRepository {
    fun createChargeLock(userId: String): BalanceChargeLock

    fun deleteChargeLock(chargeLockId: String)

    fun getNullableChargeLock(chargeLockId: String): BalanceChargeLock?

    fun getNullableByUserId(userId: String): Balance?

    fun create(userId: String, balance: BigDecimal = BigDecimal.ZERO): Balance

    fun getNullableByUserIdWithLock(userId: String): Balance?

    fun getByUserId(userId: String): Balance

    fun modify(modifyBalance: ModifyBalance): Balance
}