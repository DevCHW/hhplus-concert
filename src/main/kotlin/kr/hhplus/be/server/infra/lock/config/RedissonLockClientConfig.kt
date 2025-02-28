package kr.hhplus.be.server.infra.lock.config

import kr.hhplus.be.server.infra.lock.RedissonLockClient
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class RedissonLockClientConfig {

    @Bean
    fun redissonClient(env: Environment): RedissonClient {
        val host = env.getProperty("spring.data.redis.host")
        val port = env.getProperty("spring.data.redis.port")
        val config = Config()
        config
            .useSingleServer()
            .setAddress("redis://$host:$port")
        return Redisson.create(config)
    }

    @Bean
    fun redissonLockClient(env: Environment): RedissonLockClient {
        return RedissonLockClient(redissonClient(env))
    }

}