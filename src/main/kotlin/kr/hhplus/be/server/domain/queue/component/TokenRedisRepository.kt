package kr.hhplus.be.server.domain.queue.component

import java.util.*

interface TokenRedisRepository {
    fun createToken(userId: String): UUID?
    fun removeTokens(tokens: Set<UUID>)
    fun remove(userId: String)
    fun getNullableToken(userId: String): UUID?
}