package kr.hhplus.be.server.domain.client

import kr.hhplus.be.server.domain.client.dto.ReservationCompleteDto
import org.springframework.stereotype.Component

@Component
class DataPlatformService(
    private val dataPlatformClient: DataPlatformClient,
) {

    fun sendData(data: ReservationCompleteDto) {
        dataPlatformClient.sendData(data)
    }

}