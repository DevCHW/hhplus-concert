package kr.hhplus.be.server.domain.queue.component

import java.util.*

interface WaitingQueueRedisRepository {
    fun addToken(token: UUID): Boolean?

    fun getRank(token: UUID): Long?

    fun delete(token: UUID): Long?

    fun getTokens(start: Long, end: Long): Set<UUID>

    fun removeRange(start: Long, end: Long): Long?
}