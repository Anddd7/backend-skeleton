package com.github.anddd7.order.api

import com.github.anddd7.order.model.Product
import java.math.BigDecimal

class ProductDTO(
    val name: String,
    val price: BigDecimal
)

fun List<Product>.toDTO() = map {
    ProductDTO(
        name = it.name,
        price = it.price
    )
}
