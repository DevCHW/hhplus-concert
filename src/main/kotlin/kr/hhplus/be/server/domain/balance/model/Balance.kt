package kr.hhplus.be.server.domain.balance.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "balance")
class Balance(
    @Column(name = "user_id")
    var userId: String,

    @Column(name = "balance")
    var balance: BigDecimal,

    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(createdAt = createdAt, updatedAt = updatedAt) {

    fun charge(amount: BigDecimal): Balance {
        this.balance = this.balance.plus(amount)
        return this
    }
}