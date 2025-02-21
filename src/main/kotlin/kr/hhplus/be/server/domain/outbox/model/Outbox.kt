package kr.hhplus.be.server.domain.outbox.model

import java.time.LocalDateTime

data class Outbox(
    val idempotencyKey: String,
    val topic: String,
    val key: String?,
    val message: String,
    var status: Status,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    fun delivered() {
        this.status = Status.DELIVERED
    }

    enum class Status {
        PENDING, DELIVERED
    }

}
