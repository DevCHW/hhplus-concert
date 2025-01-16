package kr.hhplus.be.server.infra.storage.core

import jakarta.persistence.EntityNotFoundException
import kr.hhplus.be.server.domain.token.TokenRepository
import kr.hhplus.be.server.domain.token.model.CreateToken
import kr.hhplus.be.server.domain.token.model.Token
import kr.hhplus.be.server.infra.storage.core.jpa.entity.TokenEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.TokenEntityJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TokenCoreRepository(
    private val tokenJpaRepository: TokenEntityJpaRepository,
) : TokenRepository {

    override fun save(createToken: CreateToken): Token {
        return tokenJpaRepository.save(
            TokenEntity(
                userId = createToken.userId,
                token = createToken.token,
            )
        ).toDomain()
    }

    override fun getByToken(token: UUID): Token {
        val tokenEntity = tokenJpaRepository.findByToken(token)
            ?: throw EntityNotFoundException("Token not found. token = $token")
        return tokenEntity.toDomain()
    }

    override fun getTokenByStatus(status: Token.Status): List<Token> {
        return tokenJpaRepository.findByStatus(TokenEntity.Status.fromDomain(status))
            .map { it.toDomain() }
    }

    override fun getTokenByStatusNotSortByIdAsc(status: Token.Status, limit: Int): List<Token> {
        if (limit == 0) return emptyList()
        return tokenJpaRepository.findByStatusNotOrderByIdAsc(TokenEntity.Status.fromDomain(status), PageRequest.of(0, limit))
            .map { it.toDomain() }
    }

    override fun deleteByIds(tokenIds: List<String>) {
        tokenJpaRepository.deleteAllByIdInBatch(tokenIds)
    }

    override fun updateStatusByIdsIn(status: Token.Status, tokenIds: List<String>): Int {
        if (tokenIds.isEmpty()) {
            return 0
        }
        return tokenJpaRepository.updateStatusByIdsIn(TokenEntity.Status.fromDomain(status), tokenIds)
    }

    override fun deleteByToken(token: UUID) {
        tokenJpaRepository.deleteByToken(token)
    }
}