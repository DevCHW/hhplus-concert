package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.payment.PaymentRepository
import kr.hhplus.be.server.domain.payment.model.CreatePayment
import kr.hhplus.be.server.domain.payment.model.Payment
import kr.hhplus.be.server.infra.storage.core.jpa.entity.PaymentEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.PaymentEntityJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentCoreRepository(
    private val paymentJpaRepository: PaymentEntityJpaRepository,
) : PaymentRepository {

    override fun save(createPayment: CreatePayment): Payment {
        val paymentEntity = PaymentEntity.create(createPayment)
        return paymentJpaRepository.save(paymentEntity).toDomain()
    }

    override fun getById(id: String): Payment {
        return paymentJpaRepository.findByIdOrThrow(id).toDomain()
    }

}