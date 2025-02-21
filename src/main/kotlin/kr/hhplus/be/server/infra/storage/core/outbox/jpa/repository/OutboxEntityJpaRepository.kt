package kr.hhplus.be.server.infra.storage.core.outbox.jpa.repository

import kr.hhplus.be.server.domain.outbox.model.Outbox
import kr.hhplus.be.server.infra.storage.core.outbox.jpa.entity.OutboxEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OutboxEntityJpaRepository : JpaRepository<OutboxEntity, String> {
    fun findByStatus(status: Outbox.Status): List<OutboxEntity>
    fun findByIdempotencyKey(idempotencyKey: String): OutboxEntity?
}