package kr.hhplus.be.server.infra.storage.core.point

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.query.Param

interface PointEntityJpaRepository : JpaRepository<PointEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findForUpdateByUserId(@Param("userId") userId: Long): PointEntity
}
