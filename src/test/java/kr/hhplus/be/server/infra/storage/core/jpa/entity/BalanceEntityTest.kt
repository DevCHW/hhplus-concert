package kr.hhplus.be.server.infra.storage.core.jpa.entity

import kr.hhplus.be.server.domain.balance.model.ModifyBalance
import kr.hhplus.be.server.support.IdGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("잔고 엔티티 단위 테스트")
class BalanceEntityTest {

    @Nested
    inner class `수정` {
        @Test
        fun `잔고 수정 객체를 통해 잔고 엔티티를 수정할 수 있다`() {
            // given
            val balanceEntity = kr.hhplus.be.server.infra.storage.core.balance.jpa.entity.BalanceEntity(
                userId = IdGenerator.generate(),
                balance = BigDecimal(0),
            )

            val modifyBalance = ModifyBalance(
                userId = balanceEntity.userId,
                balance = BigDecimal(100)
            )

            // when
            val result = balanceEntity.modify(modifyBalance)

            // then
            assertThat(result.balance).isEqualTo(BigDecimal(100))
        }
    }

}