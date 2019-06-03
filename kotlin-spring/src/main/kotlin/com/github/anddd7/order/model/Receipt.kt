package com.github.anddd7.order.model

import com.github.anddd7.order.repository.dao.ReceiptDAO
import java.math.BigDecimal
import java.util.UUID

data class Receipt(
    val id: UUID,
    val itemNumber: Int,
    val totalPrice: BigDecimal
)

fun ReceiptDAO.toModel() = Receipt(
    id, itemNumber, totalPrice
)
