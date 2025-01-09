package kr.hhplus.be.server.api.token.controller.dto.response

import kr.hhplus.be.server.api.token.controller.dto.enums.TokenStatus
import java.util.*

data class GetTokenResponse(
    val id: String,
    val token: UUID,
    val status: TokenStatus,
    val position: Int,
)