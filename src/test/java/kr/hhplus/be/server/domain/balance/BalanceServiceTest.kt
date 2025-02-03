package kr.hhplus.be.server.domain.balance

import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.balance.fixture.BalanceFixture
import kr.hhplus.be.server.support.IdGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("잔고 서비스 단위 테스트")
class BalanceServiceTest {
    private lateinit var balanceService: BalanceService
    private lateinit var balanceRepository: BalanceRepository

    @BeforeEach
    fun setUp() {
        balanceRepository = mockk(relaxed = true)
        balanceService = BalanceService(balanceRepository)
    }

    @Nested
    inner class `잔고 충전` {
        @Test
        fun `잔액을 충전 금액만큼 증가시키고 반환한다`() {
            // given
            val balance = BalanceFixture.get(balance = BigDecimal.ZERO)
            val userId = balance.userId
            val amount = BigDecimal.valueOf(100)

            every { balanceRepository.getNullableByUserId(any()) } returns balance
            every { balanceRepository.modify(any()) } returns BalanceFixture.get(balance = balance.balance.plus(amount))

            // when
            val result = balanceService.charge(userId, amount)

            // then
            assertThat(result.balance).isEqualTo(amount)
        }
    }

    @Nested
    inner class `잔고 조회` {
        @Test
        fun `유저 ID를 통해 잔고를 조회할 수 있다`() {
            // given
            val balance = BalanceFixture.get()

            every { balanceRepository.getNullableByUserId(balance.userId) } returns balance

            // when
            val result = balanceService.getBalance(balance.userId)

            // then
            assertThat(result.balance).isEqualTo(balance.balance)
        }

        @Test
        fun `조회된 잔고가 없으면 0원인 잔고를 반환한다`() {
            // given
            val userId = IdGenerator.generate()
            every { balanceRepository.getNullableByUserId(any()) } returns null

            // when
            val result = balanceService.getBalance(userId)

            // then
            assertThat(result.balance).isEqualTo(BigDecimal.ZERO)
        }
    }

    @Nested
    inner class `잔액 차감` {
        @Test
        fun `잔액을 차감 금액만큼 차감하고 반환한다`() {
            // given
            val userId = IdGenerator.generate()
            val amount = BigDecimal.valueOf(100)

            every { balanceRepository.getNullableByUserId(userId) }
                .returns(BalanceFixture.get(userId = userId, balance = BigDecimal.valueOf(200)))

            every { balanceRepository.modify(any()) }
                .returns(BalanceFixture.get(userId = userId, balance = BigDecimal.valueOf(100)))

            // when
            val result = balanceService.decreaseBalance(userId, amount)

            // then
            assertThat(result.balance).isEqualTo(BigDecimal.valueOf(100))
        }
    }

}