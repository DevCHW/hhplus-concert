package kr.hhplus.be.server.domain.user

import kr.hhplus.be.server.domain.user.model.CreateUser
import kr.hhplus.be.server.domain.user.model.User

interface UserRepository {
    fun save(createUser: CreateUser): User
}