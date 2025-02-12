package kr.hhplus.be.server.infra.storage.core.concert.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.concert.model.CreateSeat
import kr.hhplus.be.server.domain.concert.model.Seat
import kr.hhplus.be.server.infra.storage.core.support.entity.BaseEntity

@Entity
@Table(name = "seat")
class SeatEntity(
    @Column(name = "concert_schedule_id")
    val concertScheduleId: String,

    @Column(name = "number")
    val number: Int,
) : BaseEntity() {

    fun toDomain(): Seat {
        return Seat(
            id = id,
            concertScheduleId = concertScheduleId,
            number = number,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    companion object {
        fun create(createSeat: CreateSeat): SeatEntity {
            return SeatEntity(
                concertScheduleId = createSeat.concertScheduleId,
                number = createSeat.number,
            )
        }
    }
}