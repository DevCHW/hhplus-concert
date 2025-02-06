package kr.hhplus.be.server.scheduler.queue

import kr.hhplus.be.server.domain.queue.ActiveQueueService
import kr.hhplus.be.server.domain.queue.TokenService
import kr.hhplus.be.server.domain.queue.WaitingQueueService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QueueScheduler(
    private val waitingQueueService: WaitingQueueService,
    private val activeQueueService: ActiveQueueService,
    private val tokenService: TokenService,
) {

    /**
     * 활성 큐 토큰 추가
     * M 시간마다 N개씩 대기큐에서 활성큐로 옮긴다.
     * TODO : M, N 개략적 규모 측정
     */
    @Scheduled(fixedRate = ADD_ACTIVE_SCHEDULE_MILLIS)
    fun addTokenActiveQueueSchedule() {
        // 대기 큐에서 0 ~ N 토큰 조회
        val activateTargetTokens = waitingQueueService.getRange(0, ADD_ACTIVE_TOKEN_SIZE)

        // 대기큐에서 N개의 토큰 제거
        waitingQueueService.removeBySize(ADD_ACTIVE_TOKEN_SIZE)

        // 대기 큐에서 조회한 토큰 목록을 활성 큐에 삽입
        activeQueueService.addTokens(activateTargetTokens)

        // 최대 활성 시간 초과 토큰 목록 만료
        val removedTokens = activeQueueService.expireTokens(ACTIVE_TOKEN_MAX_LIFETIME_SECOND)

        // 제거한 토큰 목록 삭제
        tokenService.removeTokens(removedTokens)
    }

    companion object {
        // M 시간 (10초)
        private const val ADD_ACTIVE_SCHEDULE_MILLIS: Long = 10 * 1000

        // N 개 (1000개)
        private const val ADD_ACTIVE_TOKEN_SIZE: Long = 1000

        // 활성 큐 토큰 TTL (5분)
        private const val ACTIVE_TOKEN_MAX_LIFETIME_SECOND: Long = 5 * 60
    }

}
