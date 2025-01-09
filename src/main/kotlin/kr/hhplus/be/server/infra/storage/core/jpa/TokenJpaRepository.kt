package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.token.model.Token
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


interface TokenJpaRepository : JpaRepository<Token, String> {
    fun findByToken(token: UUID): Token?

    fun findByStatus(status: Token.Status): List<Token>

    fun findByStatusNotOrderByIdAsc(status: Token.Status, pageable: Pageable): List<Token>

    @Transactional
    @Modifying
    @Query("""
        UPDATE Token token 
        SET token.status = :status, token.updatedAt = :now
        WHERE token.id IN :ids
    """)
    fun updateStatusByIdsIn(
        @Param("status") status: Token.Status,
        @Param("ids") ids: List<String>,
        @Param("now") now: LocalDateTime = LocalDateTime.now()
    ): Int

    fun deleteByToken(token: UUID)
}