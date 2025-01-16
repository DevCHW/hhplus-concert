package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.infra.storage.core.jpa.repository.UserEntityJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(
    private val userJpaRepository: UserEntityJpaRepository,
) : UserRepository{
}