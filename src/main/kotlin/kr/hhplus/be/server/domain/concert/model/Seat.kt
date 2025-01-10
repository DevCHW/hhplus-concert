package kr.hhplus.be.server.domain.concert.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "seat")
class Seat(
    @Column(name = "concert_schedule_id")
    val concertScheduleId: String,

    @Column(name = "number")
    val number: Int,
) : BaseEntity()