package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.model.QueueToken
import java.util.*

interface QueueRepository {
    fun saveToken(queueToken: QueueToken): QueueToken
    fun getByToken(token: UUID): QueueToken
}