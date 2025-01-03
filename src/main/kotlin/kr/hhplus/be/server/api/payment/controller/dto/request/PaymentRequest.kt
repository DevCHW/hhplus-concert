package kr.hhplus.be.server.api.payment.controller.dto.request

data class PaymentRequest(
    val seatId: String,
    val concertScheduleId: String,
)
