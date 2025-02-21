package kr.hhplus.be.server.domain.outbox.model

data class CreateOutbox(
    val idempotencyKey: String,
    val topic: String,
    val key: String?,
    val message: String,
)