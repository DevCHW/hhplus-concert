package kr.hhplus.be.server.domain.balance

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "balance", schema = "hhplus")
class Balance(
    @Column(name = "user_id")
    var userId: String,

    @Column(name = "balance")
    var balance: BigDecimal,
) : BaseEntity()