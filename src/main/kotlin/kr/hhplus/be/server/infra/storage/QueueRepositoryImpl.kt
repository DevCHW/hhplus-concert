package kr.hhplus.be.server.infra.storage

import jakarta.persistence.EntityNotFoundException
import kr.hhplus.be.server.domain.queue.QueueRepository
import kr.hhplus.be.server.domain.queue.model.QueueToken
import kr.hhplus.be.server.infra.storage.core.jpa.QueueTokenJpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class QueueRepositoryImpl(
    private val queueTokenJpaRepository: QueueTokenJpaRepository,
) : QueueRepository {

    override fun saveToken(queueToken: QueueToken): QueueToken {
        return queueTokenJpaRepository.save(queueToken)
    }

    override fun getByToken(token: UUID): QueueToken {
        return queueTokenJpaRepository.findByToken(token)?: throw EntityNotFoundException("QueueToken not found. token = $token")
    }
}