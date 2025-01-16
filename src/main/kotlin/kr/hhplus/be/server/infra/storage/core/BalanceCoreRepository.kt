package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceChargeLockEntity
import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.BalanceChargeLockEntityJpaRepository
import kr.hhplus.be.server.infra.storage.core.jpa.repository.BalanceEntityJpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BalanceCoreRepository(
    private val balanceJpaRepository: BalanceEntityJpaRepository,
    private val balanceChargeLockJpaRepository: BalanceChargeLockEntityJpaRepository
) : BalanceRepository {

    override fun getByUserIdOrNull(userId: String): Balance? {
        val balanceEntity = balanceJpaRepository.findNullableByUserId(userId)
        return balanceEntity?.toDomain()
    }

    override fun create(userId: String, balance: BigDecimal): Balance {
        val balanceEntity = BalanceEntity.create(userId, balance)
        return balanceJpaRepository.save(balanceEntity).toDomain()
    }

    override fun getByUserIdWithLock(userId: String): Balance {
        return balanceJpaRepository.findForUpdateByUserId(userId).toDomain()
    }

    override fun chargeLock(userId: String): BalanceChargeLockEntity {
        return balanceChargeLockJpaRepository.save(BalanceChargeLockEntity(userId))
    }

    override fun chargeUnLock(chargeLockId: String) {
        return balanceChargeLockJpaRepository.deleteById(chargeLockId)
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