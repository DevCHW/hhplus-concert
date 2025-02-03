package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.*
import kr.hhplus.be.server.domain.queue.model.Token
import kr.hhplus.be.server.infra.storage.core.jpa.entity.converter.UUIDToStringConverter
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "token")
class TokenEntity (
    @Column(name = "user_id")
    val userId: String,

    @Convert(converter = UUIDToStringConverter::class)
    @Column(name = "token")
    val token: UUID,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: Token.Status = Token.Status.CREATED,

    updatedAt: LocalDateTime = LocalDateTime.now(),
    createdAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(createdAt = createdAt, updatedAt = updatedAt) {

    fun toDomain(): Token {
        return Token(
            id = this.id,
            userId = this.userId,
            token = this.token,
            status = this.status,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }
}

