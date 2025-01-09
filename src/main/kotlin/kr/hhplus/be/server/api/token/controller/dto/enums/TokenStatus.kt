package kr.hhplus.be.server.api.token.controller.dto.enums

import kr.hhplus.be.server.domain.token.model.Token

enum class TokenStatus {
    INACTIVE, ACTIVE;

    companion object {
        fun fromBy(tokenStatus: Token.Status): TokenStatus {
            return when (tokenStatus) {
                Token.Status.ACTIVE -> ACTIVE
                else -> INACTIVE
            }
        }
    }
}