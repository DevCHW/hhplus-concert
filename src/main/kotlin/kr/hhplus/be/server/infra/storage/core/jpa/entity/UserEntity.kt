package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.user.model.User

@Entity
@Table(name = "users")
class UserEntity (
    @Column(name = "username")
    val username: String,
) : BaseEntity() {

    fun toDomain(): User {
        return User(
            id = id,
            username = username,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

}