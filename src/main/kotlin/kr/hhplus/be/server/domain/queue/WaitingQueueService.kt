package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.component.WaitingQueueRedisRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class WaitingQueueService(
    private val waitingQueueRedisRepository: WaitingQueueRedisRepository,
) {
    // 대기열 토큰 추가
    fun addToken(token: UUID): Boolean {
        return waitingQueueRedisRepository.addToken(token) ?: throw IllegalStateException("대기열 토큰 생성에 실패하였습니다.")
    }

    // 대기열 범위 조회
    fun getRange(start: Long, end: Long): Set<UUID> {
        return waitingQueueRedisRepository.getTokens(start, end)
    }

    // 대기열 토큰 삭제
    fun removeBySize(size: Long): Long? {
        return waitingQueueRedisRepository.removeRange(0, size)
    }

    // 대기열 순위 조회
    fun getRank(token: UUID): Long? {
        return waitingQueueRedisRepository.getRank(token)
    }
}