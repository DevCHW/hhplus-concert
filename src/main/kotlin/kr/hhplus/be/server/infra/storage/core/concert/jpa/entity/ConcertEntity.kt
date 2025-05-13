package kr.hhplus.be.server.infra.storage.core.concert.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.concert.model.CreateConcert
import kr.hhplus.be.server.infra.storage.core.support.entity.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "concert")
class ConcertEntity(
    @Column(name = "title")
    val title: String,

    @Column(name = "price")
    val price: BigDecimal,
) : BaseEntity() {

    fun toDomain(): Concert {
        return Concert(
            id = this.id,
            title = this.title,
            price = this.price,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    companion object {
        fun create(createConcert: CreateConcert): ConcertEntity {
            return ConcertEntity(
                title = createConcert.title,
                price = createConcert.price,
            )
        }
    }
}