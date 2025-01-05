package kr.hhplus.be.server.domain.queue

import jakarta.persistence.*
import kr.hhplus.be.server.domain.BaseEntity

@Entity
@Table(name = "waiting_queue", schema = "hhplus")
class WaitingQueue (
    @Column(name = "user_id")
    val userId: String,

    @Column(name = "token")
    val token: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Status,
) : BaseEntity() {

    // 상태
    enum class Status (
        val description: String,
    ) {
        CREATE("생성"), ACTIVE("활성")
    }
}

