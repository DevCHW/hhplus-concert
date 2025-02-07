package kr.hhplus.be.server.infra.storage.redis

import kr.hhplus.be.server.domain.queue.repository.ActiveQueueRedisRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Repository
class ActiveQueueRedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>,
) : ActiveQueueRedisRepository {

    // 활성 큐에 토큰 목록 추가
    override fun addTokens(tokens: Set<UUID>): Long? {
        if (tokens.isNotEmpty()) {
            val tuples: Set<ZSetOperations.TypedTuple<String>> =
                tokens.map { token ->
                    ZSetOperations.TypedTuple.of(token.toString(), now())
                }.toSet()

            return activeQueue().add(ACTIVE_QUEUE_KEY, tuples)
        }
        return null
    }

    override fun removeExpiredTokens(activeTokenMaxLifeTime: Long): Set<UUID> {
        val expirationTime = now() - activeTokenMaxLifeTime

        // 만료 토큰 조회
        val expiredTokens = activeQueue()
            .rangeByScore(ACTIVE_QUEUE_KEY, 0.0, expirationTime)

        expiredTokens?.forEach {
            activeQueue().remove(ACTIVE_QUEUE_KEY, it)
        }

        return expiredTokens?.map { UUID.fromString(it) }?.toSet() ?: emptySet()
    }

    // 활성 큐에 토큰 존재 여부 조회
    override fun isExist(token: UUID): Boolean {
        val score = activeQueue().score(ACTIVE_QUEUE_KEY, token.toString())
        return score != null
    }

    override fun remove(token: UUID) {
        activeQueue().remove(ACTIVE_QUEUE_KEY, token.toString())
    }

    private fun now(): Double {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond().toDouble()
    }

    private fun activeQueue(): ZSetOperations<String, String> {
        return redisTemplate.opsForZSet()
    }

    companion object {
        const val ACTIVE_QUEUE_KEY = "active-tokens"
    }
}