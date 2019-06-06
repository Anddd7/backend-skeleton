package com.github.anddd7.order.api

import com.github.anddd7.order.service.OrderService

class OrderController(
    private val orderService: OrderService
) {
    fun getOrder() = orderService.findAll().toDTO()
}

