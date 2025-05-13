package kr.hhplus.be.server.infra.storage.core.point

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.*

@Entity
@Table(name = "point")
class PointEntity(
    val userId: Long,

    @Column(nullable = false)
    var balance: Int = 0,

    @Version
    var version: Long = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}