package kr.hhplus.be.server.domain.client

import kr.hhplus.be.server.domain.client.dto.DataPlatformPaymentCreateRequest

interface DataPlatformClient {

    fun collectData(model: DataPlatformPaymentCreateRequest)

}