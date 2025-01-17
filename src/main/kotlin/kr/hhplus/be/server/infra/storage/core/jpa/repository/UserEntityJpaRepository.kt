package kr.hhplus.be.server.infra.storage.core.jpa.repository

import kr.hhplus.be.server.infra.storage.core.jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityJpaRepository : JpaRepository<UserEntity, String> {
}