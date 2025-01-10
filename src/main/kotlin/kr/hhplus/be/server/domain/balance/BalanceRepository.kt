package kr.hhplus.be.server.domain.balance

import kr.hhplus.be.server.domain.balance.model.Balance

interface BalanceRepository {
    fun getByUserIdOrNull(userId: String): Balance?
    fun save(balance: Balance): Balance
    fun getByUserIdWithLock(userId: String): Balance
}