package kr.hhplus.be.server.domain.concert.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "concert_schedule")
class ConcertSchedule (
    @Column(name = "concert_id")
    val concertId: String,

    @Column(name = "location")
    val location: String,

    @Column(name = "concert_at")
    val concertAt: LocalDateTime,
) : BaseEntity()