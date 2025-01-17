package kr.hhplus.be.server.infra.storage.core.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.concert.model.ConcertSchedule
import kr.hhplus.be.server.domain.concert.model.CreateConcertSchedule
import java.time.LocalDateTime

@Entity
@Table(name = "concert_schedule")
class ConcertScheduleEntity (
    @Column(name = "concert_id")
    val concertId: String,

    @Column(name = "location")
    val location: String,

    @Column(name = "concert_at")
    val concertAt: LocalDateTime,
) : BaseEntity() {
    fun toDomain(): ConcertSchedule {
        return ConcertSchedule(
            id = this.id,
            concertId = this.concertId,
            location = this.location,
            concertAt = this.concertAt,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    companion object {
        fun create(createConcertSchedule: CreateConcertSchedule): ConcertScheduleEntity {
            return ConcertScheduleEntity(
                concertId = createConcertSchedule.concertId,
                location = createConcertSchedule.location,
                concertAt = createConcertSchedule.concertAt,
            )
        }
    }
}