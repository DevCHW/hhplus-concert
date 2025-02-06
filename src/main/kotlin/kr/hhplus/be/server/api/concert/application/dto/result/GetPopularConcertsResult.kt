package kr.hhplus.be.server.api.concert.application.dto.result

import kr.hhplus.be.server.domain.concert.model.Concert

data class GetPopularConcertsResult(
    val id: String,
    val title: String,
) {
    companion object {
        fun from(concert: Concert): GetPopularConcertsResult {
            return GetPopularConcertsResult(
                id = concert.id,
                title = concert.title,
            )
        }
    }
}
