package kr.hhplus.be.server.domain.concert

import com.github.f4b6a3.tsid.TsidCreator
import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.concert.fixture.ConcertScheduleFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@DisplayName("콘서트 스케줄 서비스 단위 테스트")
class ConcertScheduleEntityServiceTest {
    private lateinit var concertScheduleService: ConcertScheduleService
    private lateinit var concertScheduleRepository: ConcertScheduleRepository

    @BeforeEach
    fun setUp() {
        concertScheduleRepository = mockk()
        concertScheduleService = ConcertScheduleService(concertScheduleRepository)
    }

    @Nested
    inner class `예약 가능한 콘서트 일정 목록 조회` {
        @Test
        fun `콘서트 ID에 해당하는 예약 가능한 콘서트 일정 목록을 조회하여 반환한다`() {
            // given
            val concertId = TsidCreator.getTsid().toString()
            LocalDateTime.now()
            val concertSchedule1 = ConcertScheduleFixture.get(concertId = concertId,)
            val concertSchedule2 = ConcertScheduleFixture.get(concertId = concertId)

            every { concertScheduleRepository.getAvailableConcertSchedules(concertId) }
                .returns(listOf(concertSchedule1, concertSchedule2))

            // when
            val result = concertScheduleService.getAvailableConcertSchedules(concertId)

            // then
            assertThat(result)
                .hasSize(2)
                .extracting("concertId")
                .containsExactlyInAnyOrder(concertId, concertId)
        }
    }
}