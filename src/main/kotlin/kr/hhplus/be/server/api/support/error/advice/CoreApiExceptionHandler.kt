package io.hhplus.cleanarchitecture.api.support.error.advice

import kr.hhplus.be.server.api.support.error.CoreApiException
import kr.hhplus.be.server.api.support.error.ErrorType
import com.hhplus.board.support.response.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(1)
class CoreApiExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CoreApiException::class)
    fun handleCoreException(e: CoreApiException): ResponseEntity<ApiResponse<Any>> {
        when (e.errorType.logLevel) {
            LogLevel.ERROR -> log.error("{} : {}", e.errorType, e.errorMessage, e)
            LogLevel.FATAL -> log.error("{} : {}", e.errorType, e.errorMessage, e)
            LogLevel.WARN -> log.warn("{} : {}", e.message, e.errorMessage)
            LogLevel.INFO -> log.info("{} : {}", e.errorType, e.errorMessage)
            LogLevel.DEBUG -> log.debug("{} : {}", e.errorType, e.errorMessage)
            LogLevel.TRACE -> log.trace("{} : {}", e.errorType, e.errorMessage)
            else -> log.error("CoreException : {}", e.message)
        }
        return ResponseEntity(ApiResponse.error(e.errorType), e.errorType.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Unexpected error occurred. message:{}", e.message, e)
        return ResponseEntity(ApiResponse.error(ErrorType.SERVER_ERROR), ErrorType.SERVER_ERROR.status)
    }

}