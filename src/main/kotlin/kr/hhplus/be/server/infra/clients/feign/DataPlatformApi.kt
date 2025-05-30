package kr.hhplus.be.server.infra.clients.feign

import kr.hhplus.be.server.domain.client.dto.DataPlatformPaymentCreateRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(value = "dataplatform-api", url = "http://localhost:8080")
interface DataPlatformApi {

    @PostMapping(value = ["/api/v1/data-platform/reservations/pay"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sendData(@RequestBody request: DataPlatformPaymentCreateRequest): Map<String, Any>

}