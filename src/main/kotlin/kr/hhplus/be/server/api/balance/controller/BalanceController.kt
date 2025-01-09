package kr.hhplus.be.server.api.balance.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.balance.controller.dto.request.ChargeRequest
import kr.hhplus.be.server.api.balance.controller.dto.response.BalanceResponse
import kr.hhplus.be.server.api.balance.controller.dto.response.ChargeResponse
import kr.hhplus.be.server.domain.balance.BalanceService
import org.springframework.web.bind.annotation.*

@RestController
class BalanceController(
    private val balanceService: BalanceService,
) {

    /**
     * 잔고 충전 API
     */
    @PutMapping("/api/v1/balance/charge")
    fun charge(
        @RequestBody request: ChargeRequest,
    ): ApiResponse<ChargeResponse> {
        val data = balanceService.charge(request.userId, request.amount)
        return ApiResponse.success(ChargeResponse(data.balance))
    }

    /**
     * 잔고 조회 API
     */
    @GetMapping("/api/v1/balance")
    fun balance(
        @RequestParam("userId") userId: String,
    ): ApiResponse<BalanceResponse> {
        val data = balanceService.getBalance(userId)
        return ApiResponse.success(BalanceResponse(data.balance))
    }
}