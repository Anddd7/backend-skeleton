package com.github.anddd7.order.model

import com.github.anddd7.order.repository.dao.ProductDAO
import java.math.BigDecimal
import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val price: BigDecimal
)

fun ProductDAO.toModel() = Product(
    id, name, price
)
