package kr.hhplus.be.server.api.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorType(
    val status: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.", LogLevel.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", LogLevel.WARN),
}