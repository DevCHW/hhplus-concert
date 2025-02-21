package kr.hhplus.be.server.domain.outbox

import kr.hhplus.be.server.domain.outbox.model.CreateOutbox
import kr.hhplus.be.server.domain.outbox.model.Outbox

interface OutboxRepository {
    fun create(createOutbox: CreateOutbox): Outbox
    fun getByStatus(status: Outbox.Status): List<Outbox>
    fun save(outbox: Outbox): Outbox
}