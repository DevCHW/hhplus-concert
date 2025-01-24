package kr.hhplus.be.server.domain.balance.model

import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import java.math.BigDecimal

data class ModifyBalance(
    val userId: String,
    val balance: BigDecimal,
) {
    init {
        require(balance >= BigDecimal.ZERO) {
            throw CoreException(ErrorType.INSUFFICIENT_BALANCE)
        }
    }
}