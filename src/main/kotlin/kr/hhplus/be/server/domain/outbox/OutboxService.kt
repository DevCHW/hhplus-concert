package kr.hhplus.be.server.domain.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kr.hhplus.be.server.domain.outbox.model.CreateOutbox
import kr.hhplus.be.server.domain.outbox.model.Outbox
import kr.hhplus.be.server.domain.support.component.message.MessageManager
import kr.hhplus.be.server.domain.support.component.message.MessagePlatform
import org.springframework.stereotype.Service

@Service
class OutboxService(
    private val outboxRepository: OutboxRepository,
    private val messageManager: MessageManager,
    private val objectMapper: ObjectMapper,
) {

    fun create(
        topic: String,
        key: String? = null,
        idempotencyKey: String,
        message: Any
    ): Outbox {
        val jsonMessage = objectMapper.writeValueAsString(message)
        return outboxRepository.create(
            CreateOutbox(
                idempotencyKey = idempotencyKey,
                topic = topic,
                key = key,
                message = jsonMessage,
            )
        )
    }

    fun readPendingMessages(): List<Outbox> {
        return outboxRepository.getByStatus(Outbox.Status.PENDING)
    }

    fun send(outbox: Outbox) {
        val message = objectMapper.readValue<MutableMap<String, Any>>(outbox.message)
        messageManager.send(MessagePlatform.KAFKA, outbox.topic, outbox.key, message.toMap())
        outbox.delivered()
        outboxRepository.save(outbox)
    }

}