package kr.hhplus.be.server.infra.storage

import jakarta.persistence.EntityNotFoundException
import kr.hhplus.be.server.domain.token.TokenRepository
import kr.hhplus.be.server.domain.token.model.Token
import kr.hhplus.be.server.infra.storage.core.jpa.TokenJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TokenRepositoryImpl(
    private val tokenJpaRepository: TokenJpaRepository,
) : TokenRepository {

    override fun saveToken(token: Token): Token {
        return tokenJpaRepository.save(token)
    }

    override fun getByToken(token: UUID): Token {
        return tokenJpaRepository.findByToken(token)
            ?: throw EntityNotFoundException("Token not found. token = $token")
    }

    override fun getTokenByStatus(status: Token.Status): List<Token> {
        return tokenJpaRepository.findByStatus(status)
    }

    override fun getTokenByStatusNotSortByIdAsc(status: Token.Status, limit: Int): List<Token> {
        if (limit == 0) return emptyList()
        return tokenJpaRepository.findByStatusNotOrderByIdAsc(status, PageRequest.of(0, limit))
    }

    override fun deleteTokens(expiredTokens: List<Token>) {
        tokenJpaRepository.deleteAllInBatch(expiredTokens)
    }

    override fun updateStatusByIdsIn(status: Token.Status, inactiveTokenIds: List<String>): Int {
        if (inactiveTokenIds.isEmpty()) {
            return 0
        }
        return tokenJpaRepository.updateStatusByIdsIn(status, inactiveTokenIds)
    }

    override fun deleteByToken(token: UUID) {
        tokenJpaRepository.deleteByToken(token)
    }
}