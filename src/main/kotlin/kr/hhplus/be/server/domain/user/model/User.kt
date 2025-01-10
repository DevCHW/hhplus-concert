package kr.hhplus.be.server.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "users")
class User (
    @Column(name = "username")
    val username: String,
) : BaseEntity()