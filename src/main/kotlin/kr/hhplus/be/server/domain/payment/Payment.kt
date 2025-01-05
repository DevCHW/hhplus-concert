package kr.hhplus.be.server.domain.payment

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "payment", schema = "hhplus")
class Payment (
    @Column(name = "user_id")
    val userId: String,

    @Column(name = "reservation_id")
    val reservationId: String,

    @Column(name = "amount")
    val amount: BigDecimal,
) : BaseEntity()