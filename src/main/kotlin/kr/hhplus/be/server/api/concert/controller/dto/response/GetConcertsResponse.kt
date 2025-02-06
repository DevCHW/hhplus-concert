package kr.hhplus.be.server.api.concert.controller.dto.response

import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertsResult

data class GetConcertsResponse(
    val id: String,
    val title: String,
) {
    companion object {
        fun from(result: GetConcertsResult): GetConcertsResponse {
            return GetConcertsResponse(
                id = result.id,
                title = result.title,
            )
        }
    }
}