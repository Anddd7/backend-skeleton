package com.github.anddd7.order.service

data class NotFound(override val message: String?) : RuntimeException(message)
