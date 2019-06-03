package com.github.anddd7.order.model

import com.github.anddd7.order.repository.dao.OrderDAO
import com.github.anddd7.order.service.ProductService
import com.github.anddd7.order.service.ReceiptService
import java.util.UUID

data class Order(
    val id: UUID,
    val products: List<Product>,
    val receipt: Receipt?
)

fun OrderDAO.toModel(productService: ProductService, receiptService: ReceiptService) = Order(
    id,
    products = productService.find(productIds),
    receipt = receiptService.find(receiptId).orElse(null)
)
