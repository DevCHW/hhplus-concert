package kr.hhplus.be.server.api.balance.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.balance.controller.dto.request.ChargeRequest
import kr.hhplus.be.server.api.balance.controller.dto.response.BalanceResponse
import kr.hhplus.be.server.api.balance.controller.dto.response.ChargeResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class BalanceController {

    /**
     * 잔고 충전 API
     * TODO: 구현
     */
    @PostMapping("/api/v1/balance/charge")
    fun charge(
        @RequestBody request: ChargeRequest,
    ): ApiResponse<ChargeResponse> {
        val data = ChargeResponse(
            balance = BigDecimal.valueOf(100L),
        )
        return ApiResponse.success(data)
    }

    /**
     * 잔고 조회 API
     * TODO : 구현
     */
    @GetMapping("/api/v1/balance")
    fun balance(
        @RequestParam("userId") userId: String,
    ): ApiResponse<BalanceResponse> {
        return ApiResponse.success(BalanceResponse(BigDecimal.valueOf(100L)))
    }
}