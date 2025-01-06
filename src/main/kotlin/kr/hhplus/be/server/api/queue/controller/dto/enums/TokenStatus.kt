package kr.hhplus.be.server.api.queue.controller.dto.enums

import kr.hhplus.be.server.domain.queue.model.QueueToken

enum class TokenStatus {
    INACTIVE, ACTIVE;

    companion object {
        fun fromBy(waitingQueueStatus: QueueToken.Status): TokenStatus {
            return when (waitingQueueStatus) {
                QueueToken.Status.ACTIVE -> ACTIVE
                else -> INACTIVE
            }
        }
    }
}