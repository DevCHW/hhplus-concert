package kr.hhplus.be.server.domain.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

enum class ErrorType(
    val status: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {
    // Balance
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.", LogLevel.WARN),

    // Reservation
    NOT_PAYABLE_STATE(HttpStatus.BAD_REQUEST, "예약이 결제 가능한 상태가 아닙니다.", LogLevel.WARN),
    ALREADY_RESERVED_SEAT(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다.", LogLevel.WARN),

    // Token
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST, "토큰 값이 존재하지 않습니다.", LogLevel.WARN),
    TOKEN_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 발급받은 토큰이 존재합니다.", LogLevel.WARN),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.", LogLevel.WARN),
    INACTIVE_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 활성상태가 아닙니다.", LogLevel.WARN),

    // Lock
    GET_LOCK_FAIL(HttpStatus.BAD_REQUEST, "락 획득에 실패하였습니다.", LogLevel.ERROR),

    // Core
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.", LogLevel.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", LogLevel.WARN),
}