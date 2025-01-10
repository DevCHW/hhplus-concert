package kr.hhplus.be.server.domain.reservation.error

class SeatAlreadyReservedException(
    errorMessage: String
) : RuntimeException(errorMessage)