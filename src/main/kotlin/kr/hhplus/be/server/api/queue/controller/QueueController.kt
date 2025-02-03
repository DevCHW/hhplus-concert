package kr.hhplus.be.server.api.queue.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.queue.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.queue.controller.dto.response.CreateTokenResponse
import kr.hhplus.be.server.api.queue.controller.dto.response.GetTokenResponse
import kr.hhplus.be.server.domain.queue.QueueService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class QueueController(
    private val queueService: QueueService,
) {

    /**
     * 토큰 생성
     */
    @PostMapping("/api/v1/queue/token")
    fun createQueueToken(
        @RequestBody request: CreateTokenRequest,
    ): ApiResponse<CreateTokenResponse> {
        val data = queueService.createQueueToken(request.userId)
        return ApiResponse.success(CreateTokenResponse.from(data))
    }

    /**
     * 토큰 조회
     */
    @GetMapping("/api/v1/queue/token")
    fun getToken(
        @RequestHeader("Queue-Token") token: UUID,
    ): ApiResponse<GetTokenResponse> {
        val data = queueService.getToken(token)
        return ApiResponse.success(GetTokenResponse.from(data))
    }

}