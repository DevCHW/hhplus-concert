package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "balance_charge_lock")
class BalanceChargeLockEntity(
    @Column(name = "user_id")
    val userId: String,
) : BaseEntity()