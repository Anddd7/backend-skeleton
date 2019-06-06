package com.github.anddd7.order.api

import com.github.anddd7.order.model.OrderMapper
import com.github.anddd7.order.service.OrderService

class OrderController(
    private val orderService: OrderService,
    private val orderMapper: OrderMapper
) {
    fun getOrder() = orderMapper.toDTO(orderService.findAll())
}

