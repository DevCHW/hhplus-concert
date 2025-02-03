package kr.hhplus.be.server.api.queue.controller.dto.response

import kr.hhplus.be.server.api.queue.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.domain.queue.model.Token
import java.util.*

data class CreateTokenResponse(
    val id: String,
    val token: UUID,
    val status: TokenStatus,
) {
    companion object {
        fun from(token: Token): CreateTokenResponse {
            return CreateTokenResponse(
                id = token.id,
                token = token.token,
                status = TokenStatus.fromBy(token.status),
            )
        }
    }
}
