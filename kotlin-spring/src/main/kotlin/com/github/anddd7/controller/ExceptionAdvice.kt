package com.github.anddd7.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(InnerException::class)
    fun handleInnerException(ex: InnerException, request: WebRequest) =
        handleExceptionInternal(ex, null, HttpHeaders(), ex.status, request)

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("", ex)

        if (body != null) return super.handleExceptionInternal(ex, body, headers, status, request)

        val errors = when (ex) {
            is MethodArgumentNotValidException -> ex.bindingResult.allErrors.map { it.toError() }
            else -> listOf(Error(ex.message))
        }
        val code = (ex as? InnerException)?.code
        val response = ErrorResponse(request.contextPath, errors, code)
        return super.handleExceptionInternal(ex, response, headers, status, request)
    }

    private fun ObjectError.toError() =
        Error(defaultMessage, if (this is FieldError) field else objectName)
}

// return formatted error message
data class ErrorResponse(
    val path: String,
    val errors: List<Error>,
    val code: ErrorCode? = null
)

data class Error(
    val message: String? = null,
    val source: String? = null
)
