package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.payment.PaymentRepository
import kr.hhplus.be.server.domain.payment.model.Payment
import kr.hhplus.be.server.infra.storage.core.findByIdOrThrow
import kr.hhplus.be.server.infra.storage.core.jpa.PaymentJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository,
) : PaymentRepository {

    override fun save(payment: Payment): Payment {
        return paymentJpaRepository.save(payment)
    }

    override fun getById(id: String): Payment {
        return paymentJpaRepository.findByIdOrThrow(id)
    }

}