package kr.hhplus.be.server.domain.support.lock

enum class LockResource(
    private val description: String,
) {
    BALANCE("잔고"),
    RESERVATION("예약"),
    SEAT("좌석"),
}