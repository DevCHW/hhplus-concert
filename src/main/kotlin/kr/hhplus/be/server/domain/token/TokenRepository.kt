package kr.hhplus.be.server.domain.token

import kr.hhplus.be.server.domain.token.model.Token
import java.util.*

interface TokenRepository {
    fun saveToken(token: Token): Token

    fun getByToken(token: UUID): Token

    fun getTokenByStatus(status: Token.Status): List<Token>

    fun getTokenByStatusNotSortByIdAsc(status: Token.Status, limit: Int): List<Token>

    fun deleteTokens(expiredTokens: List<Token>)

    fun updateStatusByIdsIn(status: Token.Status, inactiveTokenIds: List<String>): Int
}