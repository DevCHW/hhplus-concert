package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceChargeLockJpaRepository : JpaRepository<BalanceChargeLock, String>
