package com.github.anddd7.order.repository.dao

import java.math.BigDecimal
import java.util.UUID

data class ReceiptDAO(
    val id: UUID,
    val itemNumber: Int,
    val totalPrice: BigDecimal
)
