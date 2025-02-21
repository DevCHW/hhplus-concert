package kr.hhplus.be.server.microservice.dataplatform.domain.ingestion

import kr.hhplus.be.server.microservice.dataplatform.consumer.message.PaymentCreateMessage
import org.springframework.stereotype.Service

@Service
class DataIngestionService(
    private val ingestionRepository: IngestionRepository,
) {

    fun ingestionPaymentData(paymentCreateMessage: PaymentCreateMessage) {
        ingestionRepository.save(paymentCreateMessage)
    }

    fun isExistByIdempotencyKey(idempotencyKey: String): Boolean {
        return ingestionRepository.existByIdempotencyKey(idempotencyKey)
    }

}