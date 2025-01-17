package kr.hhplus.be.server.api.support.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import kr.hhplus.be.server.domain.token.TokenService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class QueueTokenValidInterceptor(
    private val tokenService: TokenService
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader("QUEUE_TOKEN") ?: throw CoreException(ErrorType.BAD_REQUEST)
        val isActive = tokenService.isActive(UUID.fromString(token))
        if (!isActive) {
            throw CoreException(ErrorType.BAD_REQUEST)
        }
        return true
    }

}