package kr.hhplus.be.server.domain.reservation.model

import jakarta.persistence.*
import kr.hhplus.be.server.domain.BaseEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
class Reservation(
    @Column(name = "seat_id")
    val seatId: String,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Status,

    @Column(name = "pay_amount")
    val payAmount: BigDecimal,

    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(createdAt = createdAt, updatedAt = updatedAt) {

    fun modifyStatus(status: Status): Reservation {
        this.status = status
        return this
    }

    enum class Status(
        val description: String,
    ) {
        PENDING("대기"),
        COMPLETED("예약 완료"),
        CANCEL("예약 취소")
    }

    companion object {
        fun create(createReservation: CreateReservation): Reservation {
            return Reservation(
                seatId = createReservation.seatId,
                userId = createReservation.userId,
                payAmount = createReservation.payAmount,
                status = createReservation.status,
            )
        }
    }
}