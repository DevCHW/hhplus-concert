package kr.hhplus.be.server.api.support.error

import com.hhplus.board.support.response.ApiResponse
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.boot.logging.LogLevel
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(1)
class CoreApiExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CoreException::class)
    fun handleCoreException(e: CoreException): ResponseEntity<ApiResponse<Any>> {
        when (e.errorType.logLevel) {
            LogLevel.ERROR -> log.error("{} : {}", e.errorType, e.errorMessage, e)
            LogLevel.FATAL -> log.error("{} : {}", e.errorType, e.errorMessage, e)
            LogLevel.WARN -> log.warn("{} : {}", e.errorType, e.errorMessage)
            LogLevel.INFO -> log.info("{} : {}", e.errorType, e.errorMessage)
            LogLevel.DEBUG -> log.debug("{} : {}", e.errorType, e.errorMessage)
            LogLevel.TRACE -> log.trace("{} : {}", e.errorType, e.errorMessage)
            else -> log.error("CoreException : {}", e.message, )
        }
        return ResponseEntity(ApiResponse.error(e.errorType), e.errorType.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Unexpected error occurred. message:[{}], RequestId:[{}]", e.message, MDC.get("requestId"), e)
        return ResponseEntity(ApiResponse.error(ErrorType.SERVER_ERROR), ErrorType.SERVER_ERROR.status)
    }

}