package kr.hhplus.be.server.infra.feign.client

import kr.hhplus.be.server.domain.client.DataPlatformClient
import kr.hhplus.be.server.domain.client.dto.ReservationCompleteDto
import kr.hhplus.be.server.infra.feign.client.api.DataPlatformApi
import org.springframework.stereotype.Component

@Component
class DataPlatformClientImpl(
    private val dataPlatformApi: DataPlatformApi,
) : DataPlatformClient {

    override fun sendData(model: ReservationCompleteDto) {
        dataPlatformApi.sendData(model)
    }

}