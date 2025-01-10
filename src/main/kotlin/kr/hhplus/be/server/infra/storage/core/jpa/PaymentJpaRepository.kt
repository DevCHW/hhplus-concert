package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.payment.model.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<Payment, String> {
}