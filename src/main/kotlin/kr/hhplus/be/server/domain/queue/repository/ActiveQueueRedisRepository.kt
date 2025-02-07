package kr.hhplus.be.server.domain.queue.repository

import java.util.*

interface ActiveQueueRedisRepository {
    fun addTokens(tokens: Set<UUID>): Long?

    fun removeExpiredTokens(activeTokenMaxLifeTime: Long): Set<UUID>

    fun isExist(token: UUID): Boolean

    fun remove(token: UUID)
}