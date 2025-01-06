package kr.hhplus.be.server.infra.storage

import kr.hhplus.be.server.domain.queue.QueueRepository
import kr.hhplus.be.server.domain.queue.model.QueueToken
import kr.hhplus.be.server.infra.storage.core.jpa.QueueTokenJpaRepository
import org.springframework.stereotype.Repository

@Repository
class QueueRepositoryImpl(
    private val waitingQueueJpaRepository: QueueTokenJpaRepository,
) : QueueRepository {

    override fun saveToken(queueToken: QueueToken): QueueToken {
        return waitingQueueJpaRepository.save(queueToken)
    }

}