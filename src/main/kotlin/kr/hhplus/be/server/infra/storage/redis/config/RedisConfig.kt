package kr.hhplus.be.server.infra.storage.redis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory(env: Environment): RedisConnectionFactory {
        val host = env.getProperty<String>("spring.data.redis.host") ?: throw IllegalStateException("Redis host must be set")
        val port = env.getProperty<Int>("spring.data.redis.port") ?: throw IllegalStateException("Redis port must be set")

        val lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ZERO)
            .shutdownTimeout(Duration.ZERO)
            .build()

        val redisStandaloneConfiguration = RedisStandaloneConfiguration(host, port)
        return LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            // "\xac\xed\x00" 같은 불필요한 해시값을 보지 않기 위해 serializer 설정
            this.keySerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            setConnectionFactory(redisConnectionFactory)
        }
    }
}