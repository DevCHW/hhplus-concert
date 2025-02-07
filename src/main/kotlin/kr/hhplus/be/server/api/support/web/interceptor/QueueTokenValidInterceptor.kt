package kr.hhplus.be.server.api.support.web.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.hhplus.be.server.domain.queue.ActiveQueueService
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class QueueTokenValidInterceptor(
    private val activeQueueService: ActiveQueueService,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val tokenStr = request.getHeader("Queue-Token") ?: throw CoreException(ErrorType.TOKEN_EMPTY)
        validToken(tokenStr)
        return true
    }

    private fun validToken(tokenStr: String) {
        try {
            val uuidToken = UUID.fromString(tokenStr)
            val exist = activeQueueService.isExistToken(uuidToken)
            if (!exist) {
                throw CoreException(ErrorType.TOKEN_INACTIVE)
            }
        } catch (e: Exception) {
            throw CoreException(ErrorType.TOKEN_INVALID)
        }
    }
}