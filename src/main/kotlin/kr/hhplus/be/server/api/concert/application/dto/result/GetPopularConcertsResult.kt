package kr.hhplus.be.server.api.concert.application.dto.result

import kr.hhplus.be.server.domain.concert.model.Concert

data class GetPopularConcertsResult(
    val id: String,
    val title: String,
    val reservationCount: Int = 0,
) {
    companion object {
        fun of(concert: Concert, reservationCount: Int): GetPopularConcertsResult {
            return GetPopularConcertsResult(
                id = concert.id,
                title = concert.title,
                reservationCount = reservationCount,
            )
        }
    }
}
