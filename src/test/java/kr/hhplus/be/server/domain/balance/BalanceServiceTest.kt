package kr.hhplus.be.server.domain.balance

import com.github.f4b6a3.tsid.TsidCreator
import io.mockk.every
import io.mockk.mockk
import kr.hhplus.be.server.domain.balance.fixture.BalanceFixture
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
        balanceRepository = mockk()
        balanceService = BalanceService(balanceRepository)
    }

    @Nested
    inner class `잔고 충전` {
        @Test
        fun `사용자가 존재하는 잔고가 있을 경우 충전한다`() {
            // given
            val balance = BalanceFixture.createBalance(balance = BigDecimal.ZERO)
            val userId = balance.userId
            val amount = BigDecimal.valueOf(100)

            every { balanceRepository.getByUserIdOrNull(userId) } returns balance
            every { balanceRepository.save(any()) } returns balance

            // when
            val result = balanceService.charge(userId, amount)

            // then
            assertThat(result.balance).isEqualTo(amount)
        }

        @Test
        fun `사용자가 잔고가 없으면 새로 생성하여 충전한다`() {
            // given
            val amount = BigDecimal.valueOf(100)
            val balance = BalanceFixture.createBalance(balance = amount)
            val userId = balance.userId

            every { balanceRepository.getByUserIdOrNull(userId) } returns null
            every { balanceRepository.save(any()) } returns balance

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
            val balance = BalanceFixture.createBalance()

            every { balanceRepository.getByUserIdOrNull(balance.userId) } returns balance

            // when
            val result = balanceService.getBalance(balance.userId)

            // then
            assertThat(result.balance).isEqualTo(balance.balance)
        }

        @Test
        fun `조회된 잔고가 없으면 0원인 잔고를 반환한다`() {
            // given
            val userId = TsidCreator.getTsid().toString()
            every { balanceRepository.getByUserIdOrNull(any()) } returns null

            // when
            val result = balanceService.getBalance(userId)

            // then
            assertThat(result.balance).isEqualTo(BigDecimal.ZERO)
        }
    }

}