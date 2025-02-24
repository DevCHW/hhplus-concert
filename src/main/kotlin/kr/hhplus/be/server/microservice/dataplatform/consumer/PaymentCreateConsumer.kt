package kr.hhplus.be.server.microservice.dataplatform.consumer

import kr.hhplus.be.server.microservice.dataplatform.consumer.message.PaymentCreateCDCMessage
import kr.hhplus.be.server.microservice.dataplatform.consumer.message.PaymentCreateMessage
import kr.hhplus.be.server.microservice.dataplatform.domain.ingestion.DataIngestionService
import kr.hhplus.be.server.microservice.dataplatform.support.utils.JsonUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentCreateConsumer(
    private val ingestionService: DataIngestionService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["outbox.hhplus.outbox"],
        groupId = "data-platform",
        containerFactory = "dataPlatformKafkaListenerContainerFactory"
    )
    fun consumePaymentCreateMessageCdc(
        @Payload payload: String,
    ) {
        val data = JsonUtils.readTree(payload)
            .get("payload")
            .get("after")
        val encodedStr = JsonUtils.readTree(payload)
            .get("payload")
            .get("after")
            .get("message").toString()
        val decodedBytes: ByteArray = Base64.getDecoder().decode(encodedStr.trim('"'))
        val decodedStr = String(decodedBytes)
        val message = JsonUtils.readValue(
            decodedStr, PaymentCreateCDCMessage::class.java
        )
        if (data.get("topic").toString() == Topics.PAYMENT_CREATE) {
            val idempotencyKey = data.get("idempotency_key").toString()
            val exists = ingestionService.isExistByIdempotencyKey(idempotencyKey)
            if (exists) {
                log.warn("이미 처리된 메세지 입니다. IdempotencyKey=${idempotencyKey}")
                return
            }
            ingestionService.ingestionPaymentData(message)
        }
    }

    @KafkaListener(
        topics = [Topics.PAYMENT_CREATE],
        groupId = "data-platform",
        containerFactory = "dataPlatformKafkaListenerContainerFactory"
    )
    fun consumePaymentCreateMessage(
        @Payload payload: String,
    ) {
        val message = JsonUtils.readValue(payload, PaymentCreateMessage::class.java)
        val exists = ingestionService.isExistByIdempotencyKey(message.idempotencyKey)
        if (exists) {
            log.warn("이미 처리된 메세지 입니다. IdempotencyKey=${message.idempotencyKey}")
            return
        }
        ingestionService.ingestionPaymentData(message)
    }
}