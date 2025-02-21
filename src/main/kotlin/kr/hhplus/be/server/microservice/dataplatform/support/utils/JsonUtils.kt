package kr.hhplus.be.server.microservice.dataplatform.support.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory

class JsonUtils {

    companion object {
        private val log = LoggerFactory.getLogger(JsonUtils::class.java)
        private val objectMapper = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 알 수 없는 필드 무시

        fun <T> readValue(message: String, clazz: Class<T>): T {
            try {
                return objectMapper.readValue(message, clazz)
            } catch (e: Exception) {
                log.error("Failed to parse JSON: {}", message, e)
                throw e
            }
        }

        fun writeValueAsString(data: Any): String {
            try {
                return objectMapper.writeValueAsString(data)
            } catch (e: Exception) {
                log.error("Failed to parse JSON: {}", data, e)
                throw e
            }
        }

        fun readTree(jsonString: String): JsonNode {
            try {
                return objectMapper.readTree(jsonString)
            } catch (e: Exception) {
                log.error("Failed to parse JSON: {}", jsonString, e)
                throw e
            }
        }
    }
}