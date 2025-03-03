package kr.hhplus.be.server.microservice.dataplatform.domain.ingestion

import kr.hhplus.be.server.microservice.dataplatform.support.utils.JsonUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*

interface IngestionRepository {
    fun save(data: Any)
    fun existByIdempotencyKey(idempotencyKey: String): Boolean
}

@Repository
class MockIngestionRepository : IngestionRepository {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val wareHouse = mutableMapOf<String, Any>()

    override fun save(data: Any) {
        wareHouse[UUID.randomUUID().toString()] = data
        log.info("수집한 데이터가 웨어하우스에 저장되었습니다. Data: $data")
    }

    override fun existByIdempotencyKey(idempotencyKey: String): Boolean {
        return wareHouse.values.any { data ->
            val jsonNode = JsonUtils.readTree(JsonUtils.writeValueAsString(data))
            jsonNode["idempotencyKey"]?.asText() == idempotencyKey
        }
    }
}