package kr.hhplus.be.server.api.queue.controller.dto.response

import kr.hhplus.be.server.api.queue.application.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.application.dto.result.GetTokenResult
import java.util.*

data class GetTokenResponse(
    val token: UUID,
    val status: TokenStatus,
    val rank: Long?
) {
    companion object {
        fun from(result: GetTokenResult): GetTokenResponse {
            return GetTokenResponse(
                token = result.token,
                status = result.status,
                rank = result.rank,
            )
        }
    }
}