package kr.hhplus.be.server.domain.token.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.token.model.Token
import java.time.LocalDateTime
import java.util.*

class TokenFixture {
    companion object {
        fun createToken(
            id: String = TsidCreator.getTsid().toString(),
            userId: String = TsidCreator.getTsid().toString(),
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