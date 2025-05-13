package kr.hhplus.be.server.infra.storage.core.balance

import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BalanceCoreRepository(
    private val balanceJpaRepository: kr.hhplus.be.server.infra.storage.core.balance.jpa.repository.BalanceEntityJpaRepository,
) : BalanceRepository {

    override fun create(userId: String, balance: BigDecimal): Balance {
        val balanceEntity = BalanceEntity.create(userId, balance)
        return balanceJpaRepository.save(balanceEntity).toDomain()
    }

    override fun modify(modifyBalance: ModifyBalance): Balance {
        val balanceEntity = balanceJpaRepository.findNullableByUserId(modifyBalance.userId) ?: BalanceEntity.create(
            modifyBalance.userId
        )
        balanceEntity.modify(modifyBalance)
        return balanceJpaRepository.save(balanceEntity).toDomain()
    }

    override fun getNullableByUserId(userId: String): Balance? {
        val balanceEntity = balanceJpaRepository.findNullableByUserId(userId)
        return balanceEntity?.toDomain()
    }

    override fun getByUserId(userId: String): Balance {
        return balanceJpaRepository.findByUserId(userId)?.toDomain() ?: throw IllegalArgumentException()
    }

    override fun getNullableByUserIdWithLock(userId: String): Balance? {
        return balanceJpaRepository.findForUpdateByUserId(userId)?.toDomain()
    }


}