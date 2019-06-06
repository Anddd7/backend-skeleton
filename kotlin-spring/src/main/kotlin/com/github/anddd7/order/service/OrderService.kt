package com.github.anddd7.order.service

import com.github.anddd7.order.model.toModel
import com.github.anddd7.order.repository.OrderRepository

class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService
) {
    fun findAll() = orderRepository.findAll().map { it.toModel(productService) }
}
