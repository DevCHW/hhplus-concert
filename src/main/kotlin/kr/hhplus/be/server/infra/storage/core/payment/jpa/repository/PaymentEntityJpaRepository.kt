package kr.hhplus.be.server.infra.storage.core.payment.jpa.repository

import kr.hhplus.be.server.infra.storage.core.payment.jpa.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentEntityJpaRepository : JpaRepository<PaymentEntity, String> {
}