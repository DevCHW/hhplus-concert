package kr.hhplus.be.server.infra.storage.core.reservation.jpa.entity

import jakarta.persistence.*
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.ModifyReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.infra.storage.core.support.entity.BaseEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
class ReservationEntity(
    @Column(name = "seat_id")
    val seatId: String,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Reservation.Status,

    @Column(name = "pay_amount")
    val payAmount: BigDecimal,

    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(createdAt = createdAt, updatedAt = updatedAt) {

    fun modify(modifyReservation: ModifyReservation): ReservationEntity {
        this.status = modifyReservation.status
        return this
    }

    fun toDomain(): Reservation {
        return Reservation(
            id = this.id,
            seatId = this.seatId,
            userId = this.userId,
            status = this.status,
            payAmount = this.payAmount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    companion object {
        fun create(createReservation: CreateReservation): ReservationEntity {
            return ReservationEntity(
                seatId = createReservation.seatId,
                userId = createReservation.userId,
                payAmount = createReservation.payAmount,
                status = createReservation.status,
            )
        }
    }
}