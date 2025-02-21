package kr.hhplus.be.server.domain.client

import kr.hhplus.be.server.domain.client.dto.DataPlatformPaymentCreateRequest
import org.springframework.stereotype.Component

@Component
class DataPlatformService(
    private val dataPlatformClient: DataPlatformClient,
) {

    fun sendData(data: DataPlatformPaymentCreateRequest): Boolean {
        try {
            dataPlatformClient.collectData(data)
            return true
        } catch (e: Exception) {
            return false
        }
    }

}