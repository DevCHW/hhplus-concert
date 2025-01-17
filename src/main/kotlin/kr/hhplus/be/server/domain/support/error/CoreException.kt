package kr.hhplus.be.server.domain.support.error

class CoreException(
    val errorType: ErrorType,
    val errorMessage: String? = null,
) : RuntimeException(errorMessage?: errorType.message)
