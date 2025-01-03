package kr.hhplus.be.server.api.queue.controller.dto.response

import kr.hhplus.be.server.api.queue.controller.dto.enums.TokenStatus
import java.util.*

data class GetQueueTokenResponse(
    val token: UUID,
    val tokenStatus: TokenStatus,
    val position: Int,
)