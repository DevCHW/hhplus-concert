package kr.hhplus.be.server.api.payment.controller

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.api.payment.application.PaymentFacade
import kr.hhplus.be.server.api.payment.controller.dto.request.CreatePaymentRequest
import kr.hhplus.be.server.api.payment.controller.dto.response.CreatePaymentResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val paymentFacade: PaymentFacade,
){

    /**
     * 결제 API
     */
    @PostMapping("/api/v1/payment")
    fun createPayment(
        @RequestBody request: CreatePaymentRequest
    ): ApiResponse<CreatePaymentResponse> {
        val result = paymentFacade.createPayment(request.reservationId, request.token)
        return ApiResponse.success(CreatePaymentResponse(result.paymentId))
    }
}