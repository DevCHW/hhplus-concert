package kr.hhplus.be.server.domain.concert.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "concert")
class Concert (
    @Column(name = "title")
    val title: String,

    @Column(name = "price")
    val price: BigDecimal,
) : BaseEntity()