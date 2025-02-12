package kr.hhplus.be.server.infra.storage.core.balance.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.balance.model.Balance
import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.infra.storage.core.support.entity.BaseEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "balance")
class BalanceEntity(
    @Column(name = "user_id")
    val userId: String,

    @Column(name = "balance")
    var balance: BigDecimal,

    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(createdAt = createdAt, updatedAt = updatedAt) {

    fun toDomain(): Balance {
        return Balance(
            userId = this.userId,
            balance = this.balance,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    fun modify(modifyBalance: ModifyBalance): kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity {
        this.balance = modifyBalance.balance
        return this
    }

    companion object {
        fun create(
            userId: String,
            balance: BigDecimal = BigDecimal.ZERO,
        ): kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity {
            return kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity(
                userId = userId,
                balance = balance,
            )
        }
    }
}