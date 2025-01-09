package kr.hhplus.be.server.domain.token

import com.github.f4b6a3.tsid.TsidCreator
import io.mockk.*
import kr.hhplus.be.server.domain.token.fixture.TokenFixture
import kr.hhplus.be.server.domain.token.model.Token
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

@DisplayName("토큰 서비스 단위 테스트")
class TokenServiceTest {
    private lateinit var tokenService: TokenService
    private lateinit var tokenRepository: TokenRepository

    @BeforeEach
    fun setUp() {
        tokenRepository = mockk()
        tokenService = TokenService(tokenRepository)
    }

    @Nested
    inner class `토큰 생성` {
        @Test
        fun `유저 ID를 통해 토큰 생성을 하고 반환한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val token = TokenFixture.token(userId = userId, status = Token.Status.CREATED)

            every { tokenRepository.saveToken(any()) }
                .returns(token)

            // when
            val result = tokenService.createToken(userId)

            // then
            assertThat(result.id).isEqualTo(token.id)
            assertThat(result.userId).isEqualTo(token.userId)
            assertThat(result.status).isEqualTo(token.status)
        }
    }

    @Nested
    inner class `토큰 조회` {
        @Test
        fun `토큰을 조회할 수 있다`() {
            // given
            val tokenUUID = UUID.randomUUID()
            val token = TokenFixture.token(
                token = tokenUUID,
            )

            every { tokenRepository.getByToken(tokenUUID) } returns token

            // when
            val result = tokenService.getToken(tokenUUID)

            // then
            assertThat(result).isEqualTo(token)
        }
    }

    @Nested
    inner class `만료된 활성 상태 토큰 삭제` {
        @Test
        fun `활성 토큰 생존 시간을 통해 만료된 활성 상태 토큰 목록을 삭제한다`() {
            // given
            val now = LocalDateTime.now()
            val activeTokenLifeTime = 60L // 60초

            // 만료된 활성 토큰
            val expiredToken1 = TokenFixture.token(
                status = Token.Status.ACTIVE,
                updatedAt = now.minusSeconds(activeTokenLifeTime + 1),
            )

            // 만료되지 않은 활성 토큰
            val expiredToken2 = TokenFixture.token(
                status = Token.Status.ACTIVE,
                updatedAt = now.minusSeconds(activeTokenLifeTime + 1),
            )

            val activeTokens = listOf(expiredToken1, expiredToken2)

            every { tokenRepository.getTokenByStatus(Token.Status.ACTIVE) } returns activeTokens
            every { tokenRepository.deleteTokens(any()) } just Runs

            // when
            tokenService.expireActiveTokens(activeTokenLifeTime, now)

            // then
            verify(exactly = 1) { tokenRepository.deleteTokens(listOf(expiredToken1, expiredToken2)) }
        }

        @Test
        fun `활성 상태 토큰 목록이 여러개 있을 경우 만료된 토큰만 삭제되어야 한다`() {
            // given
            val now = LocalDateTime.now()
            val activeTokenLifeTime = 60L // 60초

            // 만료된 활성 토큰
            val expiredToken = TokenFixture.token(
                status = Token.Status.ACTIVE,
                updatedAt = now.minusSeconds(activeTokenLifeTime + 1),
            )

            // 만료되지 않은 활성 토큰
            val nonExpiredToken = TokenFixture.token(
                status = Token.Status.ACTIVE,
                updatedAt = now,
            )

            val activeTokens = listOf(expiredToken, nonExpiredToken)

            every { tokenRepository.getTokenByStatus(Token.Status.ACTIVE) } returns activeTokens
            every { tokenRepository.deleteTokens(any()) } just Runs

            // when
            tokenService.expireActiveTokens(activeTokenLifeTime, now)

            // then
            verify(exactly = 1) { tokenRepository.deleteTokens(listOf(expiredToken)) } // 만료된 토큰만 삭제가 호출되어야 한다.
        }
    }

    @Nested
    inner class `토큰 활성` {
        @Test
        fun `활성 토큰의 최대 갯수를 받아 비활성 토큰을 활성 상태로 변경한다`() {
            // given
            val activeTokenMaxSize = 2 // 활성 토큰의 최대 갯수
            val token1 = TokenFixture.token(status = Token.Status.CREATED)
            val token2 = TokenFixture.token(status = Token.Status.CREATED)
            val token3 = TokenFixture.token(status = Token.Status.CREATED)

            val inactiveTokens = listOf(token1, token2, token3)

            every { tokenRepository.getTokenByStatus(Token.Status.ACTIVE) } returns emptyList()  // 활성 토큰이 없음
            every { tokenRepository.getTokenByStatusNotSortByIdAsc(Token.Status.ACTIVE, activeTokenMaxSize) } returns inactiveTokens // 비활성 토큰 3개
            every { tokenRepository.updateStatusByIdsIn(Token.Status.ACTIVE, any()) } returns 2

            // when
            tokenService.ActivateTokens(activeTokenMaxSize)

            // then
            verify(exactly = 1) {
                tokenRepository.updateStatusByIdsIn(Token.Status.ACTIVE, any())
            }
        }

        @Test
        fun `이미 최대 활성 토큰 갯수만큼 토큰이 활성되어있는 경우 아무 작업도 하지 않는다`() {
            // given
            val activeTokenMaxSize = 5
            val activeTokens = List(5) { TokenFixture.token(status = Token.Status.ACTIVE) }

            // stubbing
            every { tokenRepository.getTokenByStatus(Token.Status.ACTIVE) } returns activeTokens
            every { tokenRepository.getTokenByStatusNotSortByIdAsc(Token.Status.ACTIVE, any()) } returns emptyList()

            // when
            tokenService.ActivateTokens(activeTokenMaxSize)

            // then
            verify(exactly = 0) { tokenRepository.updateStatusByIdsIn(any(), any()) } // 아무 작업도 하지 않아야 한다
        }

        @Test
        fun `상태를 변경할 비활성 토큰이 없는 경우 아무 작업도 하지 않는다`() {
            // given
            val activeTokenMaxSize = 5
            val activeTokens = List(4) { TokenFixture.token(status = Token.Status.ACTIVE) }

            // stubbing
            every { tokenRepository.getTokenByStatus(Token.Status.ACTIVE) } returns activeTokens
            every { tokenRepository.getTokenByStatusNotSortByIdAsc(Token.Status.ACTIVE, any()) } returns emptyList()

            // when
            tokenService.ActivateTokens(activeTokenMaxSize)

            // then
            verify(exactly = 0) { tokenRepository.updateStatusByIdsIn(any(), any()) } // 아무 작업도 하지 않아야 한다
        }

    }
}