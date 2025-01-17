package kr.hhplus.be.server.domain.user.model

import java.time.LocalDateTime

data class User(
    val id: String,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
