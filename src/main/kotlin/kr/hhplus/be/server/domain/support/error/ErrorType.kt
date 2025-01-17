package kr.hhplus.be.server.domain.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

enum class ErrorType(
    val status: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST, "토큰 값이 존재하지 않습니다.", LogLevel.WARN),
    TOKEN_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 발급받은 토큰이 존재합니다.", LogLevel.WARN),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.", LogLevel.WARN),

    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.", LogLevel.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", LogLevel.WARN),
}