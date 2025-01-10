package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock
import kr.hhplus.be.server.infra.storage.core.jpa.BalanceChargeLockJpaRepository
import kr.hhplus.be.server.infra.storage.core.jpa.BalanceJpaRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceRepositoryImpl(
    private val balanceJpaRepository: BalanceJpaRepository,
    private val balanceChargeLockJpaRepository: BalanceChargeLockJpaRepository
) : BalanceRepository {

    override fun getByUserIdOrNull(userId: String): Balance? {
        return balanceJpaRepository.findNullableByUserId(userId)
    }

    override fun save(balance: Balance): Balance {
        return balanceJpaRepository.save(balance)
    }

    override fun getByUserIdWithLock(userId: String): Balance {
        return balanceJpaRepository.findForUpdateByUserId(userId)
    }

    override fun chargeLock(userId: String): BalanceChargeLock {
        return balanceChargeLockJpaRepository.save(BalanceChargeLock(userId))
    }

    override fun chargeUnLock(chargeLockId: String) {
        return balanceChargeLockJpaRepository.deleteById(chargeLockId)
    }

    override fun getByUserId(userId: String): Balance {
        return balanceJpaRepository.findByUserId(userId)
    }

}