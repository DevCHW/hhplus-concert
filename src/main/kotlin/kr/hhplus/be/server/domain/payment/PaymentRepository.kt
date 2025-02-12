package kr.hhplus.be.server.domain.payment

import kr.hhplus.be.server.domain.payment.model.CreatePayment
import kr.hhplus.be.server.domain.payment.model.Payment

interface PaymentRepository {

    fun save(createPayment: CreatePayment): Payment

    fun getById(id: String): Payment

}