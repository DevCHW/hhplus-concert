package kr.hhplus.be.server.domain.balance

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.cleanarchitecture.support.concurrent.ConcurrencyTestUtils
import kr.hhplus.be.server.support.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicInteger

@DisplayName("잔고 서비스 통합 테스트")
class BalanceServiceIT(
    private val balanceService: BalanceService,
    private val balanceRepository: BalanceRepository,
) : IntegrationTestSupport() {

    @Nested
    inner class `잔고 충전` {
        @Test
        fun `충전 금액을 입력받아 사용자의 잔고 충전을 할 수 있다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val balance = balanceRepository.create(userId, BigDecimal(100))

            val amount = BigDecimal.valueOf(100)

            // when
            val result = balanceService.charge(balance.userId, amount)

            // then
            assertThat(result.balance).isEqualTo(balance.balance.plus(amount))
        }
    }

    @Nested
    inner class `잔고 차감` {
        @Test
        fun `차감 금액을 입력받아 사용자의 잔고를 차감할 수 있다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val balance = balanceRepository.create(userId, BigDecimal(100))

            val amount = BigDecimal(100)

            // when
            val result = balanceService.decreaseBalance(balance.userId, amount)

            // then
            assertThat(result.balance).isEqualTo(balance.balance.minus(amount))
        }

        @Test
        fun `동일한 유저에게 동시에 10번 차감 요청이 들어와도 오차 없이 처리되어야 한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val balance = balanceRepository.create(userId, BigDecimal(100))

            val amount = BigDecimal(10)

            val successCount = AtomicInteger()
            val failCount = AtomicInteger()
            val action = Runnable {
                try {
                    balanceService.decreaseBalance(balance.userId, amount)
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            }

            // when
            ConcurrencyTestUtils.executeConcurrently(10, action)
            val result = balanceRepository.getByUserId(balance.userId)

            // then
            assertThat(successCount.get()).isEqualTo(10)
            assertThat(failCount.get()).isEqualTo(0)
            assertThat(result.balance).isEqualTo(BigDecimal.ZERO)
        }
    }

    @Nested
    inner class `잔고 충전 잠금` {
        @Test
        fun `동일한 유저가 충전 잠금을 두번 시도할 경우 DataIntegrityViolationException이 발생한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            balanceService.chargeLock(userId) // first

            // when & then
            assertThatThrownBy {
                balanceService.chargeLock(userId) // second
            }
                .isInstanceOf(DataIntegrityViolationException::class.java)
        }
    }

    @Nested
    inner class `잔고 충전 잠금 해제` {
        @Test
        fun `잔고 충전 잠금 ID를 통해 잠금을 해제할 수 있다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            val lock = balanceRepository.createChargeLock(userId)

            // when
            balanceService.chargeUnLock(lock.id)
            val result = balanceRepository.getNullableChargeLock(lock.id)

            // then
            assertThat(result).isNull() // 해제 이후 락 조회 시 Null이 되어야 한다.
        }
    }
}