package kr.hhplus.be.server.api.support.web.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.hhplus.be.server.domain.queue.QueueService
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class QueueTokenValidInterceptor(
    private val queueService: QueueService
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val tokenStr = request.getHeader("Queue-Token") ?: throw CoreException(ErrorType.TOKEN_EMPTY)
        validToken(tokenStr)
        return true
    }

    private fun validToken(tokenStr: String) {
        try {
            val token = queueService.getToken(UUID.fromString(tokenStr))
            if (!token.isActive()) {
                throw CoreException(ErrorType.TOKEN_INACTIVE)
            }
        } catch (e: Exception) {
            throw CoreException(ErrorType.TOKEN_INVALID)
        }
    }
}