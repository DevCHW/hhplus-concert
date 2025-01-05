package kr.hhplus.be.server.infra.storage.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableJpaRepositories(basePackages = ["kr.hhplus.be.server.infra.storage"])
class JpaConfig {
    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return JpaTransactionManager()
    }
}