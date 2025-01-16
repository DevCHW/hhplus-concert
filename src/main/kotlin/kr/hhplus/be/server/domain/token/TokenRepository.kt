package kr.hhplus.be.server.domain.token

import kr.hhplus.be.server.domain.token.model.CreateToken
import kr.hhplus.be.server.domain.token.model.Token
import java.util.*

interface TokenRepository {
    fun save(createToken: CreateToken): Token

    fun getByToken(token: UUID): Token

    fun getTokenByStatus(status: Token.Status): List<Token>

    fun getTokenByStatusNotSortByIdAsc(status: Token.Status, limit: Int): List<Token>

    fun deleteByIds(tokenIds: List<String>)

    fun updateStatusByIdsIn(status: Token.Status, tokenIds: List<String>): Int

    fun deleteByToken(token: UUID)
}