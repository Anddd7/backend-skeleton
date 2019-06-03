package com.github.anddd7.order.repository.dao

import java.util.UUID

data class OrderDAO(
    val id: UUID,
    val productIds: List<UUID>,
    val receiptId: UUID
)
