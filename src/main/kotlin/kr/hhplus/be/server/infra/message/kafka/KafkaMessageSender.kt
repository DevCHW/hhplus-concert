package kr.hhplus.be.server.infra.message.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hhplus.be.server.domain.support.component.message.MessagePlatform
import kr.hhplus.be.server.domain.support.component.message.MessageSender
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaMessageSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : MessageSender {

    override fun isSupport(messagePlatform: MessagePlatform): Boolean {
        return messagePlatform == MessagePlatform.KAFKA
    }

    override fun send(
        topic: String,
        key: String?,
        message: Map<String, Any>
    ): Boolean {
        return try {
            val jsonMessage = objectMapper.writeValueAsString(message)
            key?.let {
                kafkaTemplate.send(topic, key, jsonMessage)
            } ?: kafkaTemplate.send(topic, jsonMessage)
            true
        } catch (e: Exception) {
            false
        }
    }

}