package kr.hhplus.be.server.api.balance.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.balance.application.BalanceFacade
import kr.hhplus.be.server.api.balance.controller.dto.request.ChargeRequest
import kr.hhplus.be.server.api.balance.controller.dto.response.ChargeResponse
import kr.hhplus.be.server.api.balance.controller.dto.response.GetBalanceResponse
import org.springframework.web.bind.annotation.*

@RestController
class BalanceController(
    private val balanceFacade: BalanceFacade,
) {

    /**
     * 잔고 충전 API
     */
    @PutMapping("/api/v1/balance/charge")
    fun charge(
        @RequestBody request: ChargeRequest,
    ): ApiResponse<ChargeResponse> {
        val data = balanceFacade.charge(request.userId, request.amount)
        return ApiResponse.success(ChargeResponse(data.balance))
    }

    /**
     * 잔고 조회 API
     */
    @GetMapping("/api/v1/balance")
    fun balance(
        @RequestParam("userId") userId: String,
    ): ApiResponse<GetBalanceResponse> {
        val data = balanceFacade.getBalance(userId)
        return ApiResponse.success(GetBalanceResponse(data.balance))
    }
}