package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.queue.model.QueueToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface QueueTokenJpaRepository : JpaRepository<QueueToken, String> {
    fun findByToken(token: UUID): QueueToken?
}