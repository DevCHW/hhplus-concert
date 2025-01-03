package kr.hhplus.be.server.api.payment.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.payment.application.PaymentFacade
import kr.hhplus.be.server.api.payment.controller.dto.request.PaymentRequest
import kr.hhplus.be.server.api.payment.controller.dto.response.PaymentResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class PaymentController{
    /**
     * 결제 API
     * TODO : 구현
     */
    @PostMapping("/api/v1/payment")
    fun pay(
        @RequestBody request: PaymentRequest
    ): ApiResponse<PaymentResponse> {
        val data = PaymentResponse(paymentId = UUID.randomUUID().toString())
        return ApiResponse.success(data)
    }
}