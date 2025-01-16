package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.*
import kr.hhplus.be.server.domain.token.model.Token
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
    val status: Status = Status.CREATED,

    updatedAt: LocalDateTime = LocalDateTime.now(),
    createdAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(createdAt = createdAt, updatedAt = updatedAt) {

    enum class Status (
        val description: String,
    ) {
        CREATED("생성"), ACTIVE("활성");

        fun toDomainStatus(): Token.Status {
            return when(this) {
                CREATED -> Token.Status.CREATED
                ACTIVE -> Token.Status.ACTIVE
            }
        }

        companion object {
            fun fromDomain(tokenStatus: Token.Status): Status {
                return when(tokenStatus) {
                    Token.Status.CREATED -> CREATED
                    Token.Status.ACTIVE -> ACTIVE
                }
            }
        }
    }

    fun toDomain(): Token {
        return Token(
            id = this.id,
            userId = this.userId,
            token = this.token,
            status = this.status.toDomainStatus(),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    companion object {
        fun fromDomain(token: Token): TokenEntity {
            return TokenEntity(
                userId = token.userId,
                token.token
            )
        }
    }
}

