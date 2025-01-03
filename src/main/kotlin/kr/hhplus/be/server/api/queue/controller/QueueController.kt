package kr.hhplus.be.server.api.queue.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.queue.application.QueueFacade
import kr.hhplus.be.server.api.queue.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.controller.dto.request.CreateQueueTokenRequest
import kr.hhplus.be.server.api.queue.controller.dto.response.CreateQueueTokenResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class QueueController {

    /**
     * 대기열 토큰 생성
     * TODO : 구현
     */
    @PostMapping("/api/v1/queue/token")
    fun getToken(
        @RequestBody request: CreateQueueTokenRequest,
    ): ApiResponse<CreateQueueTokenResponse> {
        val mockData = CreateQueueTokenResponse(
            token = UUID.randomUUID(),
            tokenStatus = TokenStatus.WAITING,
            position = 100,
        )
        return ApiResponse.success(mockData)
    }

    /**
     * 대기열 토큰 상태 조회
     * TODO : 구현
     */
    @GetMapping("/api/v1/queue/status")
    fun getStatus(
    ): ApiResponse<CreateQueueTokenResponse> {
        val mockData = CreateQueueTokenResponse(
            token = UUID.randomUUID(),
            tokenStatus = TokenStatus.WAITING,
            position = 100,
        )
        return ApiResponse.success(mockData)
    }

}