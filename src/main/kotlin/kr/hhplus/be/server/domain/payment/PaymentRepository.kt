package kr.hhplus.be.server.domain.payment

import kr.hhplus.be.server.domain.payment.model.Payment

interface PaymentRepository {
    fun save(payment: Payment): Payment
    fun getById(id: String): Payment
}