package kr.hhplus.be.server.api.balance.application

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.cleanarchitecture.support.concurrent.ConcurrencyTestUtils
import kr.hhplus.be.server.domain.balance.BalanceRepository
import kr.hhplus.be.server.support.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicInteger

@DisplayName("잔고 Facade 통합 테스트")
class BalanceFacadeIT(
    private val balanceFacade: BalanceFacade,
    private val balanceRepository: BalanceRepository,
) : IntegrationTestSupport() {

    @Nested
    inner class `잔고 충전` {
        @Test
        fun `동일한 유저에게 동시에 10번 충전 요청이 들어와도 1번만 성공해야 한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val balance = balanceRepository.create(userId)
            val amount = BigDecimal.valueOf(100)

            val successCount = AtomicInteger()
            val failCount = AtomicInteger()
            val action = Runnable {
                try {
                    balanceFacade.charge(balance.userId, amount)
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            }

            // when
            ConcurrencyTestUtils.executeConcurrently(10, action)

            // then
            assertThat(successCount.get()).isEqualTo(1)
            assertThat(failCount.get()).isEqualTo(9)
        }

        @Test
        fun `잔고 충전 이후 조회 시 충전 금액만큼 충전되어있어야 한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val balance = balanceRepository.create(userId)
            val amount = BigDecimal.valueOf(100)

            // when
            balanceFacade.charge(balance.userId, amount)
            val result = balanceRepository.getByUserId(userId)

            // then
            assertThat(result.balance).isEqualTo(amount)
        }
    }
}