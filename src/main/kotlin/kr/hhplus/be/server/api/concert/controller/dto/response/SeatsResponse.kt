package kr.hhplus.be.server.api.concert.controller.dto.response

import kr.hhplus.be.server.api.concert.application.dto.GetAvailableSeatResult

data class SeatsResponse(
    val id: String,
    val number: Int,
) {
    companion object {
        fun from(result: GetAvailableSeatResult): SeatsResponse {
            return SeatsResponse(
                id = result.id,
                number = result.number,
            )
        }
    }
}