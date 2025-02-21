package kr.hhplus.be.server.infra.message.kafka.payment

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentTopicConfig {

    @Bean
    fun paymentCreateTopic(): NewTopic {
        return NewTopic("payment-create", 3, 1.toShort())
    }

}