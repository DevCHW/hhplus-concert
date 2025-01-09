package kr.hhplus.be.server.scheduler

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hhplus.be.server.domain.token.TokenService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("토큰 스케줄러 단위 테스트")
class TokenSchedulerTest {
    private lateinit var tokenScheduler: TokenScheduler
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setUp() {
        tokenService = mockk()
        tokenScheduler = TokenScheduler(tokenService)
    }

    @Nested
    inner class `활성 토큰 만료 및 비활성 토큰 활성 상태로 변경` {
        @Test
        fun `expireActiveTokens와 ActivateTokens가 차례대로 호출된다`() {
            // given
            val activeTokenTtlSeconds = 3 * 60L // 3분
            val activeTokenSizeMaxSize = 1000
            every { tokenService.expireActiveTokens(any(), any()) } returns Unit // expireActiveTokens에 대한 Mock
            every { tokenService.ActivateTokens(any()) } returns Unit // ActivateTokens에 대한 Mock

            // when
            tokenScheduler.expireActiveTokensAndActivateTokens()

            // then
            verify(exactly = 1) {
                tokenService.expireActiveTokens(activeTokenTtlSeconds, any())
            }
            verify(exactly = 1) {
                tokenService.ActivateTokens(activeTokenSizeMaxSize)
            }
        }
    }
}