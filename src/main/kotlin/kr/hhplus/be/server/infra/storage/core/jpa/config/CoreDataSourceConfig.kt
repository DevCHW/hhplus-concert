package kr.hhplus.be.server.infra.storage.core.jpa.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy

@Configuration
class CoreDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun hikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    @Primary
    fun dataSource(): HikariDataSource {
        return HikariDataSource(hikariConfig())
    }

    @Bean
    fun lazyDataSource(): LazyConnectionDataSourceProxy {
        return LazyConnectionDataSourceProxy(dataSource())
    }

}