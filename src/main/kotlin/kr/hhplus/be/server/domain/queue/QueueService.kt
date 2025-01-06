package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.model.QueueToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class QueueService(
    private val repository: QueueRepository,
) {

    /**
     * 대기열 토큰 생성
     */
    fun createToken(userId: String): QueueToken {
        return repository.saveToken(
            QueueToken(
                userId = userId,
                token = UUID.randomUUID(),
            )
        )
    }
}