package kr.hhplus.be.server.api.token.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.token.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.token.controller.dto.response.CreateTokenResponse
import kr.hhplus.be.server.api.token.controller.dto.response.GetTokenResponse
import kr.hhplus.be.server.domain.token.TokenService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TokenController(
    private val tokenService: TokenService,
) {

    /**
     * 토큰 생성
     */
    @PostMapping("/api/v1/token")
    fun getToken(
        @RequestBody request: CreateTokenRequest,
    ): ApiResponse<CreateTokenResponse> {
        val data = tokenService.createToken(request.userId)
        return ApiResponse.success(CreateTokenResponse.from(data))
    }

    /**
     * 토큰 조회
     */
    @GetMapping("/api/v1/token/{token}")
    fun getStatus(
        @PathVariable("token") token: UUID,
    ): ApiResponse<GetTokenResponse> {
        val data = tokenService.getToken(token)
        return ApiResponse.success(GetTokenResponse.from(data))
    }

}