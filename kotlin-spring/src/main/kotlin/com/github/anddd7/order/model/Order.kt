package com.github.anddd7.order.model

import com.github.anddd7.order.repository.dao.OrderDAO
import com.github.anddd7.order.service.ProductService
import java.util.UUID

data class Order(
    val id: UUID,
    val products: List<Product>,
    val receipt: Receipt?
)

fun OrderDAO.toModel(productService: ProductService) = Order(
    id,
    products = productService.find(productIds),
    receipt = receipt?.toModel()
)
