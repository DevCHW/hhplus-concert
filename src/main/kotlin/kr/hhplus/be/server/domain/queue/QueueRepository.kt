package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.model.CreateToken
import kr.hhplus.be.server.domain.queue.model.Token
import java.util.*

interface QueueRepository {
    fun save(createToken: CreateToken): Token

    fun getByToken(token: UUID): Token

    fun getNullableByToken(token: UUID): Token?

    fun getTokenByStatus(status: Token.Status): List<Token>

    fun getTokenByStatusNotSortByIdAsc(status: Token.Status, limit: Int): List<Token>

    fun deleteByIds(tokenIds: List<String>)

    fun updateStatusByIdsIn(status: Token.Status, tokenIds: List<String>): Int

    fun deleteByToken(token: UUID)

    fun isExistByUserId(userId: String): Boolean
}