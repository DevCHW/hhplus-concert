package kr.hhplus.be.server.infra.storage.redis

import kr.hhplus.be.server.domain.queue.component.TokenRedisRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TokenRedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : TokenRedisRepository {

    override fun createToken(userId: String): UUID? {
        val token = UUID.randomUUID()
        val success = redisTemplate.opsForValue()
            .setIfAbsent("${TOKEN_KEY_PREFIX}:${userId}", token.toString()) ?: false

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

                // 키의 값이 삭제할 토큰 목록에 포함되어 있다면 즉시 삭제
                if (value in tokenStrings) {
                    redisTemplate.delete(key)
                }
            }
        }
    }

    override fun remove(userId: String) {
        redisTemplate.delete("${TOKEN_KEY_PREFIX}:${userId}")
    }

    override fun getNullableToken(userId: String): UUID? {
        return redisTemplate.opsForValue()
            .get("${TOKEN_KEY_PREFIX}:${userId}")
            ?.let { UUID.fromString(it) }
    }

    companion object {
        const val TOKEN_KEY_PREFIX = "tokens"
    }

}