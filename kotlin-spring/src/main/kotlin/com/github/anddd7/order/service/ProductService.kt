package com.github.anddd7.order.service

import com.github.anddd7.order.model.ProductMapper
import com.github.anddd7.order.repository.ProductRepository
import java.util.UUID

class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {
    fun find(ids: List<UUID>) = productMapper.fromDAO(productRepository.findAllById(ids))
}
