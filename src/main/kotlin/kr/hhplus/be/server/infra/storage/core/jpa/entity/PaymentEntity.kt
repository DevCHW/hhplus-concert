package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.payment.model.CreatePayment
import kr.hhplus.be.server.domain.payment.model.Payment
import java.math.BigDecimal

@Entity
@Table(name = "payment")
class PaymentEntity (
    @Column(name = "user_id")
    val userId: String,

    @Column(name = "reservation_id")
    val reservationId: String,

    @Column(name = "amount")
    val amount: BigDecimal,
) : BaseEntity() {

    fun toDomain(): Payment {
        return Payment(
            id = this.id,
            userId = this.userId,
            reservationId = this.reservationId,
            amount = this.amount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    companion object {
        fun create(createPayment: CreatePayment): PaymentEntity {
            return PaymentEntity(
                userId = createPayment.userId,
                reservationId = createPayment.reservationId,
                amount = createPayment.amount,
            )
        }
    }
}