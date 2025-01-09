package kr.hhplus.be.server.api.concert.application.dto

import kr.hhplus.be.server.domain.concert.model.Seat

data class GetAvailableSeatResult(
    val id: String,
    val number: Int,
) {
    companion object {
        fun from(seat: Seat): GetAvailableSeatResult {
            return GetAvailableSeatResult(
                id = seat.id,
                number = seat.number,
            )
        }
    }
}
