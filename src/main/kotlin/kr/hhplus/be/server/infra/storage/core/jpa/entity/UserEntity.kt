package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity (
    @Column(name = "username")
    val username: String,
) : BaseEntity()