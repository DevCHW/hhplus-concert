package kr.hhplus.be.server.infra.storage.core.point

import io.hhplus.cleanarchitecture.support.concurrent.ConcurrencyTestUtils2
import kr.hhplus.be.server.support.IntegrationTestSupport
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class PointServiceTest(
    private val pointService: PointService,
    private val pointRepository: PointEntityJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `낙관적 락, 비관적 락 경합 발생 검증 테스트`() {
        // given
        val point = pointRepository.save(
            PointEntity(
                userId = 1,
                balance = 1000,
            )
        )

        // 충전
        val chargeTask = Runnable {
            pointService.charge(point.userId, 100)
        }

        // 사용
        val useTask = Runnable {
            pointService.use(point.userId, 100)
        }

        val tasks = listOf(chargeTask, useTask)

        // when - 동시 실행
        ConcurrencyTestUtils2.executeConcurrently(tasks)

        // then - 결과
        val result = pointRepository.findById(point.id).orElseThrow()

        println("최종 잔액: ${result.balance}, version: ${result.version}")
        assertThat(result.balance).isEqualTo(1000)
    }
}