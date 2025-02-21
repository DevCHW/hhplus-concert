package kr.hhplus.be.server.infra.storage.core.outbox

import jakarta.persistence.EntityNotFoundException
import kr.hhplus.be.server.domain.outbox.OutboxRepository
import kr.hhplus.be.server.domain.outbox.model.CreateOutbox
import kr.hhplus.be.server.domain.outbox.model.Outbox
import kr.hhplus.be.server.infra.storage.core.outbox.jpa.entity.OutboxEntity
import kr.hhplus.be.server.infra.storage.core.outbox.jpa.repository.OutboxEntityJpaRepository
import org.springframework.stereotype.Repository

@Repository
class OutboxCoreRepository(
    private val outboxEntityJpaRepository: OutboxEntityJpaRepository,
) : OutboxRepository {

    override fun create(createOutbox: CreateOutbox): Outbox {
        return outboxEntityJpaRepository.save(OutboxEntity.from(createOutbox))
            .toDomain()
    }

    override fun getByStatus(status: Outbox.Status): List<Outbox> {
        return outboxEntityJpaRepository.findByStatus(status)
            .map { it.toDomain() }
    }

    override fun save(outbox: Outbox): Outbox {
        val outboxEntity = outboxEntityJpaRepository.findByIdempotencyKey(outbox.idempotencyKey) ?: throw EntityNotFoundException("OutboxEntity not found.")
        outboxEntity.modify(outbox.status)
        return outboxEntityJpaRepository.save(outboxEntity).toDomain()
    }

}