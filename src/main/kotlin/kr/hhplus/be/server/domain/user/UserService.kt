package kr.hhplus.be.server.domain.user

import kr.hhplus.be.server.domain.user.model.CreateUser
import kr.hhplus.be.server.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser(createUser: CreateUser): User {
        return userRepository.save(createUser)
    }

}