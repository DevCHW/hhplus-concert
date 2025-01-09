package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.balance.model.Balance
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceJpaRepository : JpaRepository<Balance, String> {
    fun findNullableByUserId(userId: String): Balance?
}