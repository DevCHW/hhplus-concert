package kr.hhplus.be.server.domain.concert

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "concert_schedule", schema = "hhplus")
class ConcertSchedule (
    @Column(name = "concert_id")
    val concertId: String,

    @Column(name = "concert_at")
    val concertAt: LocalDateTime,
) : BaseEntity()