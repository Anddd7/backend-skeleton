package com.github.anddd7.order.api

import com.github.anddd7.order.model.Order

class OrderDTO(
    val products: List<ProductDTO>,
    val receipt: ReceiptDTO?
)

fun List<Order>.toDTO() = map {
    OrderDTO(
        products = it.products.toDTO(),
        receipt = it.receipt?.toDTO()
    )
}
