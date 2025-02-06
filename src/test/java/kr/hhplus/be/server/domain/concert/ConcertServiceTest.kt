package kr.hhplus.be.server.domain.concert

import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("콘서트 서비스 단위 테스트")
class ConcertServiceTest {
    private lateinit var concertService: ConcertService
    private lateinit var concertRepository: ConcertRepository

    @BeforeEach
    fun setUp() {
        concertRepository = mockk()
        concertService = ConcertService(concertRepository)
    }

    @Nested
    inner class `콘서트 조회` {
        @Test
        fun `콘서트 ID를 통해 콘서트를 조회하고 반환한다`() {
            // given
            val concert = ConcertFixture.get()

            every { concertRepository.getById(concert.id) } returns concert

            // when
            val result = concertService.getConcert(concert.id)

            // then
            assertThat(result.id).isEqualTo(concert.id)
            assertThat(result.title).isEqualTo(concert.title)
            assertThat(result.price).isEqualTo(concert.price)
        }
    }
}