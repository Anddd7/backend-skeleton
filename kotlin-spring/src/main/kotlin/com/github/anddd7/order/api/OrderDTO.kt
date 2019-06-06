package com.github.anddd7.order.api

class OrderDTO(
    val products: List<ProductDTO>,
    val receipt: ReceiptDTO?
)
