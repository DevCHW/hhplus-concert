package kr.hhplus.be.server.support.testcontainers

import jakarta.annotation.PreDestroy
import kr.hhplus.be.server.support.testcontainers.MySQLContainer.Companion.mySqlContainer
import org.springframework.context.annotation.Configuration

@Configuration
class TestcontainersConfig {

    @PreDestroy
    fun preDestroy() {
        if (mySQLContainer.isRunning) mySQLContainer.stop()
        if (redisContainer.isRunning) redisContainer.stop()
    }

    companion object {
        val mySQLContainer = MySQLContainer.mySqlContainer
        val redisContainer = RedisContainer.redisContainer

        init {
            // mysql
            System.setProperty("spring.datasource.hikari.jdbc-url", mySqlContainer.jdbcUrl + "?characterEncoding=UTF-8&serverTimezone=UTC")
            System.setProperty("spring.datasource.hikari.username", mySqlContainer.username)
            System.setProperty("spring.datasource.hikari.password", mySqlContainer.password)

            // redis
            System.setProperty("spring.data.redis.host", redisContainer.host)
            System.setProperty("spring.data.redis.port", redisContainer.firstMappedPort.toString())
        }
    }
}
