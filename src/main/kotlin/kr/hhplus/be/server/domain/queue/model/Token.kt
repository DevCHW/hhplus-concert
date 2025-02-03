package kr.hhplus.be.server.domain.queue.model

import java.time.LocalDateTime
import java.util.*

data class Token(
    val id: String,
    val userId: String,
    val token: UUID,
    val status: Status,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    fun isActive(): Boolean {
        return status == Status.ACTIVE
    }

    enum class Status (
        val description: String,
    ) {
        CREATED("생성"), ACTIVE("활성")
    }

}
