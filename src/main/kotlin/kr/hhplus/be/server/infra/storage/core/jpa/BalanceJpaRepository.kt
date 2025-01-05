package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.balance.Balance
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceJpaRepository : JpaRepository<Balance, String> {
}