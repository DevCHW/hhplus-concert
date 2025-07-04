package kr.hhplus.be.server.infra.storage.redis.queue

import kr.hhplus.be.server.domain.queue.repository.TokenRedisRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Repository
import java.util.*

// Redis에서 사용할 토큰 키의 접두사
private const val TOKEN_KEY_PREFIX = "tokens"

/**
 * Redis를 사용하여 토큰을 저장하고 관리하는 TokenRedisRepository 구현체
 */
@Repository
class TokenRedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : TokenRedisRepository {

    override fun createToken(userId: String): UUID? {
        val token = UUID.randomUUID()
        // 키가 존재하지 않을 때만 토큰 설정 시도
        val success = redisTemplate.opsForValue()
            .setIfAbsent("$TOKEN_KEY_PREFIX:${userId}", token.toString()) ?: false

        if (!success) {
            return null
        }

        return token
    }

    override fun removeTokens(tokens: Set<UUID>) {
        val tokenStrings = tokens.map { it.toString() }

        redisTemplate.scan(
            ScanOptions.scanOptions()
                .match("$TOKEN_KEY_PREFIX:*")
                .build()
        ).use { cursor ->
            while (cursor.hasNext()) {
                val key = cursor.next()
                val value = redisTemplate.opsForValue().get(key)

                // 토큰이 삭제 대상 목록에 포함되어 있으면 해당 키를 삭제
                if (value in tokenStrings) {
                    redisTemplate.delete(key)
                }
            }
        }
    }


    override fun remove(userId: String) {
        redisTemplate.delete("$TOKEN_KEY_PREFIX:${userId}")
    }


    override fun getNullableToken(userId: String): UUID? {
        return redisTemplate.opsForValue()
            .get("$TOKEN_KEY_PREFIX:${userId}")
            ?.let { UUID.fromString(it) }
    }

}