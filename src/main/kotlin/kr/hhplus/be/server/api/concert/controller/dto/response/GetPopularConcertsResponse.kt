package kr.hhplus.be.server.api.concert.controller.dto.response

import kr.hhplus.be.server.api.concert.application.dto.result.GetPopularConcertsResult

data class GetPopularConcertsResponse(
    val id: String,
    val title: String,
) {
    companion object {
        fun from(result: GetPopularConcertsResult): GetPopularConcertsResponse {
            return GetPopularConcertsResponse(
                id = result.id,
                title = result.title,
            )
        }
    }
}
