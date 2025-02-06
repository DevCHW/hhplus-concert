package kr.hhplus.be.server.api.queue.application.dto.result

import kr.hhplus.be.server.api.queue.application.dto.enums.TokenStatus
import java.util.*

data class CreateWaitingQueueTokenResult(
    val token: UUID,
    val status: TokenStatus,
    val rank: Long?
)