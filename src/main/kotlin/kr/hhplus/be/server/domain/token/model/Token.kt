package kr.hhplus.be.server.domain.token.model

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

    enum class Status (
        val description: String,
    ) {
        CREATED("생성"), ACTIVE("활성")
    }

}
