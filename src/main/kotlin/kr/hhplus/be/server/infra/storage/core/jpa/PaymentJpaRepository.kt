package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<Payment, String> {
}