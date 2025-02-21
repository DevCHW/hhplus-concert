package kr.hhplus.be.server.infra.feign.client

import kr.hhplus.be.server.domain.client.DataPlatformClient
import kr.hhplus.be.server.domain.client.dto.DataPlatformPaymentCreateRequest
import kr.hhplus.be.server.infra.feign.client.api.DataPlatformApi
import org.springframework.stereotype.Component

@Component
class DataPlatformClientImpl(
    private val dataPlatformApi: DataPlatformApi,
) : DataPlatformClient {
    override fun collectData(model: DataPlatformPaymentCreateRequest) {
        dataPlatformApi.sendData(model)
    }
}