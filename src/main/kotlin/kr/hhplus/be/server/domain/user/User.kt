package kr.hhplus.be.server.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "users", schema = "hhplus")
class User (
    @Column(name = "username")
    val username: String,
) : BaseEntity()