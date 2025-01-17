package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceChargeLockEntity
import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.BalanceChargeLockEntityJpaRepository
import kr.hhplus.be.server.infra.storage.core.jpa.repository.BalanceEntityJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BalanceCoreRepository(
    private val balanceJpaRepository: BalanceEntityJpaRepository,
    private val balanceChargeLockJpaRepository: BalanceChargeLockEntityJpaRepository
) : BalanceRepository {

    override fun getNullableByUserId(userId: String): Balance? {
        val balanceEntity = balanceJpaRepository.findNullableByUserId(userId)
        return balanceEntity?.toDomain()
    }

    override fun create(userId: String, balance: BigDecimal): Balance {
        val balanceEntity = BalanceEntity.create(userId, balance)
        return balanceJpaRepository.save(balanceEntity).toDomain()
    }

    override fun getNullableByUserIdWithLock(userId: String): Balance? {
        return balanceJpaRepository.findForUpdateByUserId(userId)?.toDomain()
    }

    override fun createChargeLock(userId: String): BalanceChargeLock {
        return balanceChargeLockJpaRepository.save(BalanceChargeLockEntity(userId)).toDomain()
    }

    override fun deleteChargeLock(chargeLockId: String) {
        return balanceChargeLockJpaRepository.deleteById(chargeLockId)
    }

    override fun getNullableChargeLock(chargeLockId: String): BalanceChargeLock? {
        return balanceChargeLockJpaRepository.findByIdOrNull(chargeLockId)?.toDomain()
    }

    override fun getByUserId(userId: String): Balance {
        return balanceJpaRepository.findByUserId(userId).toDomain()
    }

    override fun modify(modifyBalance: ModifyBalance): Balance {
        val balanceEntity = balanceJpaRepository.findNullableByUserId(modifyBalance.userId) ?: BalanceEntity.create(
            modifyBalance.userId
        )
        return balanceJpaRepository.save(balanceEntity.modify(modifyBalance))
            .toDomain()
    }

}