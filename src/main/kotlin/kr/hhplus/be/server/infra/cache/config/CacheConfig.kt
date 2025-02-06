package kr.hhplus.be.server.infra.cache.config

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(redisConnectionFactory: LettuceConnectionFactory): CacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())) // Serialize 관련 설정
//            .entryTtl(Duration.ofMinutes(2)) // 캐시 기본 ttl 2분 설정
//            .disableCachingNullValues() // Null 캐싱 제외

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(configuration)
            .build()
    }

}