package kr.hhplus.be.server.api.token.controller

import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.token.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.api.token.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.token.controller.dto.response.CreateTokenResponse
import kr.hhplus.be.server.api.token.controller.dto.response.GetTokenResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TokenController {

    /**
     * 토큰 생성
     * TODO : 구현
     */
    @PostMapping("/api/v1/token")
    fun getToken(
        @RequestBody request: CreateTokenRequest,
    ): ApiResponse<CreateTokenResponse> {
        val mockData = CreateTokenResponse(
            id = TsidCreator.getTsid().toString(),
            token = UUID.randomUUID(),
            status = TokenStatus.INACTIVE,
        )
        return ApiResponse.success(mockData)
    }

    /**
     * 토큰 상태 조회
     * TODO : 구현
     */
    @GetMapping("/api/v1/token/{token}")
    fun getStatus(
        @PathVariable("token") token: String,
    ): ApiResponse<GetTokenResponse> {
        val mockData = GetTokenResponse(
            id = TsidCreator.getTsid().toString(),
            token = UUID.randomUUID(),
            status = TokenStatus.ACTIVE,
            position = 100,
        )
        return ApiResponse.success(mockData)
    }

}