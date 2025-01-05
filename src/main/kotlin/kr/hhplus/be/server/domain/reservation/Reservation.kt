package kr.hhplus.be.server.domain.reservation

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "reservation", schema = "hhplus")
class Reservation (
    @Column(name = "concert_schedule_id")
    val concertScheduleId: String,

    @Column(name = "seat_id")
    val seatId: String,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "status")
    val status: String,
) : BaseEntity()