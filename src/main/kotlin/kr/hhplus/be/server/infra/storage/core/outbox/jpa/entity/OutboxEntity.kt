package kr.hhplus.be.server.infra.storage.core.outbox.jpa.entity

import jakarta.persistence.*
import kr.hhplus.be.server.domain.outbox.model.CreateOutbox
import kr.hhplus.be.server.domain.outbox.model.Outbox
import kr.hhplus.be.server.infra.storage.core.support.entity.BaseEntity

@Entity
@Table(name = "outbox")
class OutboxEntity(
    @Column(name = "idempotency_key")
    val idempotencyKey: String,

    @Column(name = "topic")
    val topic: String,

    @Column(name = "event_key")
    val key: String?,

    @Column(name = "message")
    val message: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Outbox.Status,
) : BaseEntity() {

    fun modify(status: Outbox.Status) {
        this.status = status
    }

    fun toDomain(): Outbox {
        return Outbox(
            idempotencyKey = idempotencyKey,
            topic = topic,
            key = key,
            message = message,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    companion object {
        fun from(
            createOutbox: CreateOutbox
        ): OutboxEntity {
            return OutboxEntity(
                idempotencyKey = createOutbox.idempotencyKey,
                topic = createOutbox.topic,
                key = createOutbox.key,
                message = createOutbox.message,
                status = Outbox.Status.PENDING
            )
        }
    }
}