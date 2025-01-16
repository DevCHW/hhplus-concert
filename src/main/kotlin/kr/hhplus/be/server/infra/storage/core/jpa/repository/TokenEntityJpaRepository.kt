package kr.hhplus.be.server.infra.storage.core.jpa.repository

import kr.hhplus.be.server.infra.storage.core.jpa.entity.TokenEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


interface TokenEntityJpaRepository : JpaRepository<TokenEntity, String> {
    fun findByToken(token: UUID): TokenEntity?

    fun findByStatus(status: TokenEntity.Status): List<TokenEntity>

    fun findByStatusNotOrderByIdAsc(status: TokenEntity.Status, pageable: Pageable): List<TokenEntity>

    @Transactional
    @Modifying
    @Query(
        """
        UPDATE TokenEntity token 
        SET token.status = :status, token.updatedAt = :now
        WHERE token.id IN :ids
    """
    )
    fun updateStatusByIdsIn(
        @Param("status") status: TokenEntity.Status,
        @Param("ids") ids: List<String>,
        @Param("now") now: LocalDateTime = LocalDateTime.now()
    ): Int

    fun deleteByToken(token: UUID)
}