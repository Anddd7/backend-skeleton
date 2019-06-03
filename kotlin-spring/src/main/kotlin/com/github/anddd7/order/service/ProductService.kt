package com.github.anddd7.order.service

import com.github.anddd7.order.model.toModel
import com.github.anddd7.order.repository.ProductRepository
import java.util.UUID

class ProductService(
    val productRepository: ProductRepository
) {
    fun find(ids: List<UUID>) = productRepository.findAllById(ids).map { it.toModel() }
}
