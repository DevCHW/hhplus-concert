package kr.hhplus.be.server.infra.lock.config

import kr.hhplus.be.server.infra.lock.LettuceLockClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class LettuceLockClientConfig {

    @Bean
    fun lettuceLockClient(redisTemplate: RedisTemplate<String, String>): LettuceLockClient {
        return LettuceLockClient(redisTemplate)
    }

}