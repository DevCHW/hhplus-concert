package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, String> {
}