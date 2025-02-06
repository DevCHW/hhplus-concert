package kr.hhplus.be.server.infra.storage.redis

import kr.hhplus.be.server.domain.queue.repository.WaitingQueueRedisRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class WaitingQueueRedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>,
) : WaitingQueueRedisRepository {

    // 대기열 토큰 생성
    override fun addToken(token: UUID): Boolean? {
        // 순서 조회
        val newScore = redisTemplate.opsForValue()
            .increment(WAITING_QUEUE_COUNTER) ?: 1

        // 토큰 추가
        return redisTemplate.opsForZSet()
            .add(WAITING_QUEUE_KEY, token.toString(), newScore.toDouble())
    }

    // 대기열 순서 조회
    override fun getRank(token: UUID): Long? {
        return redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, token.toString())?.plus(1)
    }

    // 대기열 토큰 제거
    override fun delete(token: UUID): Long? {
        return redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, token.toString())
    }

    // 범위 조회
    override fun getTokens(start: Long, end: Long): Set<UUID> {
        val tokenStrings = redisTemplate.opsForZSet().range(WAITING_QUEUE_KEY, start, end) ?: emptySet()
        return tokenStrings.map { UUID.fromString(it) }.toSet()
    }

    // 범위만큼 대기 큐 토큰 삭제
    override fun removeRange(start: Long, end: Long): Long? {
        // 범위 만큼 삭제
        val removeResultSize = redisTemplate.opsForZSet()
            .removeRange(WAITING_QUEUE_KEY, start, end)
            ?.let {
                // 대기 큐 카운터 조정
                redisTemplate.opsForValue().decrement(WAITING_QUEUE_COUNTER, it)
            }
        return removeResultSize
    }

    companion object {
        const val WAITING_QUEUE_KEY = "waiting-tokens"
        const val WAITING_QUEUE_COUNTER = "waiting-queue-counter"
    }
}