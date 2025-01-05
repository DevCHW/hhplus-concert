package kr.hhplus.be.server.domain.concert

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "seat", schema = "hhplus")
class Seat(
    @Column(name = "concert_schedule_id")
    val concertScheduleId: String,

    @Column(name = "location")
    val location: String,

    @Column(name = "number")
    val number: Int,
) : BaseEntity()