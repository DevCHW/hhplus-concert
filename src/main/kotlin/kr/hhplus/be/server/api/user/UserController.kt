package kr.hhplus.be.server.api.user

import kr.hhplus.be.server.domain.user.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    /**
     * TODO: 구현하기
     */
//    @PostMapping("/api/v1/users")
//    fun createUser(
//        @RequestBody request: CreateUserRequest
//    ): ApiResponse<CreateUserResponse> {
//        userService.createUser(request.toDomain())
//    }
}