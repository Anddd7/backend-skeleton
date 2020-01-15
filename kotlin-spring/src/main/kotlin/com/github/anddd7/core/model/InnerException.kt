package com.github.anddd7.core.model

import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.http.HttpStatus

open class InnerException(
    val code: ErrorCode,
    val status: HttpStatus = HttpStatus.OK,
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)

enum class ErrorCode(@JsonValue val code: String) {
    NotFound("0000404")
}
