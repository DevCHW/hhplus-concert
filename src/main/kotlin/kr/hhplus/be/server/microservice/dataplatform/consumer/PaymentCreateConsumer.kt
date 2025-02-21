package kr.hhplus.be.server.microservice.dataplatform.consumer

import kr.hhplus.be.server.microservice.dataplatform.consumer.message.PaymentCreateMessage
import kr.hhplus.be.server.microservice.dataplatform.support.utils.JsonUtils
import kr.hhplus.be.server.microservice.dataplatform.domain.ingestion.DataIngestionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PaymentCreateConsumer(
    private val ingestionService: DataIngestionService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = [Topics.PAYMENT_CREATE], groupId = "data-platform", containerFactory = "dataPlatformKafkaListenerContainerFactory")
    fun consumePaymentCreateMessage(
        @Payload payload: String
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