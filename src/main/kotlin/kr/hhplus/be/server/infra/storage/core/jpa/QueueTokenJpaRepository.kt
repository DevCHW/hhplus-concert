package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.queue.model.QueueToken
import org.springframework.data.jpa.repository.JpaRepository

interface QueueTokenJpaRepository : JpaRepository<QueueToken, String> {
}