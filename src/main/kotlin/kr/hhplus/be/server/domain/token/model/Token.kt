package kr.hhplus.be.server.domain.token.model

import jakarta.persistence.*
import kr.hhplus.be.server.domain.BaseEntity
import kr.hhplus.be.server.domain.token.model.converter.UUIDToStringConverter
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "token")
class Token (
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
) : BaseEntity(createdAt, updatedAt) {

    enum class Status (
        val description: String,
    ) {
        CREATED("생성"), ACTIVE("활성")
    }
}

