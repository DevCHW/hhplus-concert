package kr.hhplus.be.server.infra.lock.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kr.hhplus.be.server.infra.lock.NamedLockClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class NamedLockClientConfig {

    @Bean
    fun namedLockDataSource(env: Environment): HikariDataSource {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = env.getProperty("spring.datasource.hikari.jdbc-url")
            username = env.getProperty("spring.datasource.hikari.username")
            password = env.getProperty("spring.datasource.hikari.password")
            driverClassName = "com.mysql.cj.jdbc.Driver"
            poolName = "namedlock-pool"
            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 30000
            maxLifetime = 1800000
            connectionTimeout = 30000
        }
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun namedLockClient(env: Environment): NamedLockClient {
        return NamedLockClient(namedLockDataSource(env))
    }
}