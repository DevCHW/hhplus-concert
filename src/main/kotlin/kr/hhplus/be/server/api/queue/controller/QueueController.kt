package kr.hhplus.be.server.api.queue.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.queue.application.QueueFacade
import kr.hhplus.be.server.api.queue.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.queue.controller.dto.response.CreateTokenResponse
import kr.hhplus.be.server.api.queue.controller.dto.response.GetTokenResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class QueueController(
    private val queueFacade: QueueFacade,
) {

    /**
     * 토큰 생성
     */
    @PostMapping("/api/v1/queue/token")
    fun createQueueToken(
        @RequestBody request: CreateTokenRequest,
    ): ApiResponse<CreateTokenResponse> {
        val data = queueFacade.createWaitingQueueToken(request.userId)
        return ApiResponse.success(CreateTokenResponse.from(data))
    }

    /**
     * 토큰 조회
     */
    @GetMapping("/api/v1/queue/token")
    fun getToken(
        @RequestHeader("Queue-Token") token: UUID,
        @RequestHeader(AUTHORIZATION) userId: String,
    ): ApiResponse<GetTokenResponse> {
        val data = queueFacade.getToken(token, userId)
        return ApiResponse.success(GetTokenResponse.from(data))
    }

}