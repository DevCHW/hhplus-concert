package kr.hhplus.be.server.api.concert.controller.dto.response

import kr.hhplus.be.server.domain.concert.model.Concert

data class GetPopularConcertsResponse(
    val id: String,
    val title: String,
) {
    companion object {
        fun from(concert: Concert): GetPopularConcertsResponse {
            return GetPopularConcertsResponse(
                id = concert.id,
                title = concert.title,
            )
        }
    }
}
