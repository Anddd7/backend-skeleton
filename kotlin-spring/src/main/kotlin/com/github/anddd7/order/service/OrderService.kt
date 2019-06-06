package com.github.anddd7.order.service

import com.github.anddd7.order.model.OrderMapper
import com.github.anddd7.order.repository.OrderRepository

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper
) {
    fun findAll() = orderMapper.fromDAO(orderRepository.findAll())
}
