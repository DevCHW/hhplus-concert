package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.balance.model.BalanceChargeLock

@Entity
@Table(name = "balance_charge_lock")
class BalanceChargeLockEntity(
    @Column(name = "user_id")
    val userId: String,
) : BaseEntity() {

    fun toDomain(): BalanceChargeLock {
        return BalanceChargeLock(
            id = this.id,
            userId = this.userId,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}