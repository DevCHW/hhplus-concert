package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.infra.storage.core.jpa.BalanceJpaRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceRepositoryImpl(
    private val balanceJpaRepository: BalanceJpaRepository,
) : BalanceRepository {

    override fun getByUserIdOrNull(userId: String): Balance? {
        return balanceJpaRepository.findNullableByUserId(userId)
    }

    override fun save(balance: Balance): Balance {
        return balanceJpaRepository.save(balance)
    }

}