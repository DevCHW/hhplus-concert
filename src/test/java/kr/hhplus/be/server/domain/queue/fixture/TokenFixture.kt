package kr.hhplus.be.server.domain.queue.fixture

import kr.hhplus.be.server.domain.queue.model.Token
import kr.hhplus.be.server.support.IdGenerator
import java.time.LocalDateTime
import java.util.*

class TokenFixture {
    companion object {
        fun get(
            id: String = IdGenerator.generate(),
            userId: String = IdGenerator.generate(),
            token: UUID = UUID.randomUUID(),
            status: Token.Status = Token.Status.ACTIVE,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Token {
            return Token(
                id = id,
                userId = userId,
                token = token,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}