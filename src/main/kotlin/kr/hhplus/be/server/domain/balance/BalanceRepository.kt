package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock

interface BalanceRepository {
    fun getByUserIdOrNull(userId: String): Balance?
    fun save(balance: Balance): Balance
    fun getByUserIdWithLock(userId: String): Balance
    fun chargeLock(userId: String): BalanceChargeLock
    fun chargeUnLock(chargeLockId: String)
    fun getByUserId(userId: String): Balance
}