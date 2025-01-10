package kr.hhplus.be.server.infra.storage.core.jpa

import jakarta.persistence.LockModeType
import kr.hhplus.be.server.domain.balance.model.Balance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface BalanceJpaRepository : JpaRepository<Balance, String> {
    fun findNullableByUserId(userId: String): Balance?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findForUpdateByUserId(userId: String): Balance

    fun findByUserId(userId: String): Balance
}