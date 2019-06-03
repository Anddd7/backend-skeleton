package com.github.anddd7.order.repository.dao

import java.math.BigDecimal
import java.util.UUID

data class ProductDAO(
    val id: UUID,
    val name: String,
    val price: BigDecimal
)
