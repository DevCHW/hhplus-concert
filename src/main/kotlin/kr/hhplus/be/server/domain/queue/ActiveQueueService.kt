package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.repository.ActiveQueueRedisRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ActiveQueueService(
    private val activeQueueRedisRepository: ActiveQueueRedisRepository,
) {
    // 활성 큐에 토큰 목록 추가
    fun addTokens(tokens: Set<UUID>): Long? {
        return activeQueueRedisRepository.addTokens(tokens)
    }

    // 활성 큐에 존재 여부 조회
    fun isExistToken(token: UUID): Boolean {
        return activeQueueRedisRepository.isExist(token)
    }

    // 만료
    fun expireTokens(activeTokenMaxLifetimeMillis: Long): Set<UUID> {
        return activeQueueRedisRepository.removeExpiredTokens(activeTokenMaxLifetimeMillis)
    }

    fun delete(token: UUID) {
        activeQueueRedisRepository.remove(token)
    }

}