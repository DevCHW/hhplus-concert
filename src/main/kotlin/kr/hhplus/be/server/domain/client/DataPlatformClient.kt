package kr.hhplus.be.server.domain.client

import kr.hhplus.be.server.domain.client.dto.ReservationCompleteDto

interface DataPlatformClient {

    fun sendData(model: ReservationCompleteDto)

}