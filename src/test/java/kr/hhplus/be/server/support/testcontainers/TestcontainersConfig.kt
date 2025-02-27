package kr.hhplus.be.server.support.testcontainers

import jakarta.annotation.PreDestroy
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class TestcontainersConfig {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @PreDestroy
    fun preDestroy() {
        try {
            if (mysqlContainer.isRunning) mysqlContainer.stop()
            if (redisContainer.isRunning) redisContainer.stop()
            if (kafkaContainer.isRunning) kafkaContainer.stop()
        } catch (e: Exception) {
            log.error("Error while stopping containers", e)
        }
    }

    companion object {
        private val mysqlContainer = MySQLContainer.mySqlContainer
        private val redisContainer = RedisContainer.redisContainer
        private val kafkaContainer = KafkaContainer.kafkaContainer

        init {
            // mysql
            System.setProperty("spring.datasource.hikari.jdbc-url", mysqlContainer.jdbcUrl + "?characterEncoding=UTF-8&serverTimezone=UTC")
            System.setProperty("spring.datasource.hikari.username", mysqlContainer.username)
            System.setProperty("spring.datasource.hikari.password", mysqlContainer.password)

            // redis
            System.setProperty("spring.data.redis.host", redisContainer.host)
            System.setProperty("spring.data.redis.port", redisContainer.firstMappedPort.toString())

            // kafka
            System.setProperty("spring.kafka.producer.bootstrap-servers", kafkaContainer.bootstrapServers)
            System.setProperty("spring.kafka.consumer.bootstrap-servers", kafkaContainer.bootstrapServers)
        }
    }
}
