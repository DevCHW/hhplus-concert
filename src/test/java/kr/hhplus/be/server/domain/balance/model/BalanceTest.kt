package kr.hhplus.be.server.domain.balance.model

import kr.hhplus.be.server.domain.balance.fixture.BalanceFixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("잔고 단위 테스트")
class BalanceTest {

    @Nested
    inner class `잔고 충전` {
        @Test
        fun `충전 금액을 입력받아 잔고 충전을 할 수 있다`() {
            // given
            val balance = BalanceFixture.createBalance(balance = BigDecimal.valueOf(100))
            val amount = BigDecimal.valueOf(100)

            // when
            balance.charge(amount)

            // then
            assertThat(balance.balance).isEqualTo(BigDecimal.valueOf(200))
        }
    }


    @Nested
    inner class `잔고 차감` {
        @Test
        fun `차감 금액을 입력받아 잔고 차감을 할 수 있다`() {
            // given
            val balance = BalanceFixture.createBalance(balance = BigDecimal.valueOf(100))
            val amount = BigDecimal.valueOf(100)

            // when
            balance.decrease(amount)

            // then
            assertThat(balance.balance).isEqualTo(BigDecimal.valueOf(0))
        }

        @Test
        fun `잔고 차감 금액이 기존 잔액보다 높은 경우 IllegalArgumentException 예외가 발생한다`() {
            // given
            val balance = BalanceFixture.createBalance(balance = BigDecimal.valueOf(100))
            val amount = BigDecimal.valueOf(101)

            // when & then
            assertThatThrownBy {
                balance.decrease(amount)
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("잔액이 부족합니다.")
        }
    }
}