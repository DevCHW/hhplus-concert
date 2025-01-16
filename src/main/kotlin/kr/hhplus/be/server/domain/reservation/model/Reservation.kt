package kr.hhplus.be.server.domain.reservation.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Reservation(
    val id: String,
    val seatId: String,
    val userId: String,
    val status: Status,
    val payAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    enum class Status(
        val description: String,
    ) {
        PENDING("대기"),
        COMPLETED("예약 완료"),
        CANCEL("예약 취소")
    }
}
