package kr.hhplus.be.server.api.queue.controller.dto.response

import kr.hhplus.be.server.api.queue.application.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.application.dto.result.CreateWaitingQueueTokenResult
import java.util.*

data class CreateTokenResponse(
    val token: UUID,
    val status: TokenStatus,
    val rank: Long?,
) {
    companion object {
        fun from(result: CreateWaitingQueueTokenResult): CreateTokenResponse {
            return CreateTokenResponse(
                token = result.token,
                status = result.status,
                rank = result.rank,
            )
        }
    }
}
