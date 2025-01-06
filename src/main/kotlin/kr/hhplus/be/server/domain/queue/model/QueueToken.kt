package kr.hhplus.be.server.domain.queue.model

import jakarta.persistence.*
import kr.hhplus.be.server.domain.BaseEntity
import java.util.*

@Entity
@Table(name = "queue_token")
class QueueToken (
    @Column(name = "user_id")
    val userId: String,

    @Column(name = "token")
    val token: UUID,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: Status = Status.CREATED,
) : BaseEntity() {

    enum class Status (
        val description: String,
    ) {
        CREATED("생성"), ACTIVE("활성")
    }
}

