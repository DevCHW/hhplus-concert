package kr.hhplus.be.server.api.support.mock

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DataPlatformMockApiController {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/api/v1/data-platform/reservations/pay")
    fun addPayReservation(
        @RequestBody request: Map<String, Any>
    ): Map<String, Any> {
        log.info("Mock API 호출, data = $request")

        val response = mutableMapOf<String, Any>()
        response["result"] = "Success"
        return response
    }

}