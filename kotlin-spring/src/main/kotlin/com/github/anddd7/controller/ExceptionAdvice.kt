package com.github.anddd7.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RuntimeException::class)
    fun handleGenericException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        logger.error("", ex)
        val headers = HttpHeaders()
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return handleExceptionInternal(ex, ex.message, headers, status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.map {
            if (it is FieldError) it.field + ": " + it.defaultMessage
            else it.defaultMessage
        }

        return handleExceptionInternal(
            ex, errors, headers, status, request
        )
    }
}