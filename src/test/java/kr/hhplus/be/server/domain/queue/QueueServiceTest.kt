package kr.hhplus.be.server.domain.queue

import com.github.f4b6a3.tsid.TsidCreator
import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.queue.fixture.QueueFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("대기열 서비스 단위 테스트")
class QueueServiceTest {
    private lateinit var waitingQueueService: QueueService
    private lateinit var waitingQueueTokenRepository: QueueRepository

    @BeforeEach
    fun setUp() {
        waitingQueueTokenRepository = mockk()
        waitingQueueService = QueueService(waitingQueueTokenRepository)
    }

    @Nested
    inner class `대기열 토큰 생성` {
        @Test
        fun `유저 ID를 통해 대기열 토큰 생성을 하고 반환한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val waitingQueue = QueueFixture.queueToken(userId = userId)
            every { waitingQueueTokenRepository.saveToken(any()) }
                .returns(waitingQueue)

            // when
            val result = waitingQueueService.createToken(userId)

            // then
            assertThat(result.id).isEqualTo(waitingQueue.id)
            assertThat(result.userId).isEqualTo(waitingQueue.userId)
        }
    }

    @Nested
    inner class `대기열 토큰 상태 조회` {
        @Test
        fun `대기열 토큰 상태를 조회할 수 있다`() {
            // given
            val token = UUID.randomUUID()
            val waitingQueueToken = QueueFixture.queueToken(
                token = token,
            )
            every { waitingQueueTokenRepository.getByToken(token) } returns waitingQueueToken

            // when
            val result = waitingQueueService.getTokenStatus(token)

            // then
            assertThat(result).isEqualTo(waitingQueueToken.status)
        }
    }
}