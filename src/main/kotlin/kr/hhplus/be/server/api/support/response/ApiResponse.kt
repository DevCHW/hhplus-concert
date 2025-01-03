package com.hhplus.board.support.response

import kr.hhplus.be.server.api.support.error.ErrorType
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T> private constructor(
    val result: ApiResultType,
    val data: T? = null,
    val error: Error? = null,
) {
    companion object {
        fun success(): ApiResponse<Any> {
            return ApiResponse(ApiResultType.SUCCESS, null)
        }

        fun <S> success(data: S): ApiResponse<S> {
            return ApiResponse(ApiResultType.SUCCESS, data)
        }

        fun <S> error(errorType: ErrorType): ApiResponse<S> {
            return ApiResponse(ApiResultType.ERROR, error = Error(errorType.name, errorType.message))
        }

        fun <S> error(errorType: ErrorType, message: String): ApiResponse<S> {
            return ApiResponse(ApiResultType.ERROR, error = Error(errorType.name, message))
        }

    }

    data class Error(
        val code: String,
        val message: String,
    )
}