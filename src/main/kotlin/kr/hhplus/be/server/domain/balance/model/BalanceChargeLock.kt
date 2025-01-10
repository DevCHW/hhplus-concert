package kr.hhplus.be.server.domain.balance.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "balance_charge_lock")
class BalanceChargeLock(
    @Column(name = "user_id")
    val userId: String,
) : BaseEntity()