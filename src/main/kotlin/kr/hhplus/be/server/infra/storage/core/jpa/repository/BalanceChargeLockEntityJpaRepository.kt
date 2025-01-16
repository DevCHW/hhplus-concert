package kr.hhplus.be.server.infra.storage.core.jpa.repository

import kr.hhplus.be.server.infra.storage.core.jpa.entity.BalanceChargeLockEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceChargeLockEntityJpaRepository : JpaRepository<BalanceChargeLockEntity, String>
