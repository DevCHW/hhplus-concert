package kr.hhplus.be.server.domain.token.model

import java.util.*

data class CreateToken(
    val userId: String,
    val token: UUID,
)
