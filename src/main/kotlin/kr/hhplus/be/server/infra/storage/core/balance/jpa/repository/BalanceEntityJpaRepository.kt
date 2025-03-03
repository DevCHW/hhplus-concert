package kr.hhplus.be.server.infra.storage.core.balance.jpa.repository

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface BalanceEntityJpaRepository : JpaRepository<kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity, String> {
    fun findNullableByUserId(userId: String): kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findForUpdateByUserId(userId: String): kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity?

    fun findByUserId(userId: String): kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity
}