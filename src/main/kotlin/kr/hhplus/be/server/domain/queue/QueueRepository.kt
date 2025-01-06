package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.model.QueueToken

interface QueueRepository {
    fun saveToken(queueToken: QueueToken): QueueToken
}