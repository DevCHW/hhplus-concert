package kr.hhplus.be.server.microservice.dataplatform.infra.message.kafka.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

//@Configuration
class CDCConsumerConfig(
    private val env: Environment,
) {
//    @Bean
//    fun cdcConsumerFactory(): ConsumerFactory<String, String> {
//        return DefaultKafkaConsumerFactory(
//            mapOf(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to env.getProperty("spring.kafka.consumer.bootstrap-servers"),
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
//                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
//            )
//        )
//    }
//
//    @Bean
//    fun cdcKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
//        val kafkaListenerContainerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()
//        kafkaListenerContainerFactory.consumerFactory = cdcConsumerFactory()
//        return kafkaListenerContainerFactory
//    }
}