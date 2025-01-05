package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.queue.WaitingQueue
import org.springframework.data.jpa.repository.JpaRepository

interface WaitingQueueJpaRepository : JpaRepository<WaitingQueue, String> {
}