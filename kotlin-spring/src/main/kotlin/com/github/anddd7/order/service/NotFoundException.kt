package com.github.anddd7.order.service

data class NotFoundException(override val message: String?) : RuntimeException(message)
