package kr.hhplus.be.server.infra.storage.core.jpa.repository

import jakarta.persistence.LockModeType
import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface BalanceEntityJpaRepository : JpaRepository<BalanceEntity, String> {
    fun findNullableByUserId(userId: String): BalanceEntity?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findForUpdateByUserId(userId: String): BalanceEntity?

    fun findByUserId(userId: String): BalanceEntity
}