package kr.hhplus.be.server.domain.concert

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "concert", schema = "hhplus")
class Concert (
    @Column(name = "title")
    val title: String,
) : BaseEntity()