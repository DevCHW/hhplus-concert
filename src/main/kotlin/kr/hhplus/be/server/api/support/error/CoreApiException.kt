package kr.hhplus.be.server.api.support.error

class CoreApiException(
    val errorType: ErrorType,
    val errorMessage: String? = null,
) : RuntimeException(errorMessage?: errorType.message)
