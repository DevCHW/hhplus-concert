package kr.hhplus.be.server.infra.clients.feign

import kr.hhplus.be.server.domain.client.DataPlatformClient
import kr.hhplus.be.server.domain.client.dto.DataPlatformPaymentCreateRequest
import kr.hhplus.be.server.infra.clients.feign.DataPlatformApi
import org.springframework.stereotype.Component

@Component
class DataPlatformFeignClient(
    private val dataPlatformApi: DataPlatformApi,
) : DataPlatformClient {
    override fun collectData(model: DataPlatformPaymentCreateRequest) {
        dataPlatformApi.sendData(model)
    }
}