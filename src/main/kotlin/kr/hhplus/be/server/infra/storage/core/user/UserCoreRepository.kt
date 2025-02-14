package kr.hhplus.be.server.infra.storage.core.user

import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.domain.user.model.CreateUser
import kr.hhplus.be.server.domain.user.model.User
import kr.hhplus.be.server.infra.storage.core.user.jpa.entity.UserEntity
import kr.hhplus.be.server.infra.storage.core.user.jpa.repository.UserEntityJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(
    private val userJpaRepository: UserEntityJpaRepository,
) : UserRepository{

    override fun save(createUser: CreateUser): User {
        val userEntity = userJpaRepository.save(
            UserEntity(
                createUser.username
            )
        )
        return userEntity.toDomain()
    }
}