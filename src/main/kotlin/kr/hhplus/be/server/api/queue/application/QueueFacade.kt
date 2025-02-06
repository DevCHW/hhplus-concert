package kr.hhplus.be.server.api.queue.application

import kr.hhplus.be.server.api.queue.application.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.application.dto.result.CreateWaitingQueueTokenResult
import kr.hhplus.be.server.api.queue.application.dto.result.GetTokenResult
import kr.hhplus.be.server.domain.queue.ActiveQueueService
import kr.hhplus.be.server.domain.queue.TokenService
import kr.hhplus.be.server.domain.queue.WaitingQueueService
import org.springframework.stereotype.Component
import java.util.*

@Component
class QueueFacade(
    private val activeQueueService: ActiveQueueService,
    private val waitingQueueService: WaitingQueueService,
    private val tokenService: TokenService,
) {

    // 토큰 생성
    fun createWaitingQueueToken(userId: String): CreateWaitingQueueTokenResult {
        // 유저 ID를 통해 토큰 생성
        val token = tokenService.generateToken(userId)

        // 대기열에 토큰 추가
        waitingQueueService.addToken(token)

        // 대기열 순위 조회
        val rank = waitingQueueService.getRank(token)

        return CreateWaitingQueueTokenResult(
            token = token,
            status = TokenStatus.INACTIVE,
            rank = rank,
        )
    }

    // 토큰 조회
    fun getToken(token: UUID, userId: String): GetTokenResult {
        // 활성 큐에서 토큰 조회
        val exist = activeQueueService.isExistToken(token)

        // 활성 큐에 없으면 INACTIVE, 있으면 INACTIVE
        val status = if (exist) TokenStatus.ACTIVE else TokenStatus.INACTIVE

        // 대기 큐 순위 조회
        val rank = waitingQueueService.getRank(token)

        return GetTokenResult(
            userId = userId,
            token = token,
            status = status,
            rank = rank,
        )
    }

    // 토큰 조회
    fun isActiveToken(token: UUID): Boolean {
        return activeQueueService.isExistToken(token)
    }

}