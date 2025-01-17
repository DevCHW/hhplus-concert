package kr.hhplus.be.server.scheduler.token

import kr.hhplus.be.server.domain.token.TokenService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TokenScheduler(
    private val tokenService: TokenService,
) {
    /**
     * 토큰 만료 및 비활성 토큰을 활성 상태로 변경
     */
    @Scheduled(cron = "*/10 * * * * *")
    fun expireActiveTokensAndActivateTokens() {
        tokenService.expireActiveTokens(ACTIVE_TOKEN_TTL_SECONDS, LocalDateTime.now())
        tokenService.ActivateTokens(ACTIVE_TOKEN_SIZE_MAX_SIZE)
    }

    companion object {
        // 최대 활성 토큰 수 (1000개)
        private const val ACTIVE_TOKEN_SIZE_MAX_SIZE: Int = 1000

        // 활성 토큰 만료 시간 (3분)
        private const val ACTIVE_TOKEN_TTL_SECONDS: Long = 3 * 60
    }

}
