package com.github.anddd7.order.api

import com.github.anddd7.order.model.Receipt
import java.math.BigDecimal

class ReceiptDTO(
    val itemNumber: Int,
    val totalPrice: BigDecimal
)

fun Receipt.toDTO() = ReceiptDTO(
    itemNumber = itemNumber,
    totalPrice = totalPrice
)
