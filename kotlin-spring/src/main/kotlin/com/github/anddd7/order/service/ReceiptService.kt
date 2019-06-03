package com.github.anddd7.order.service

import com.github.anddd7.order.model.toModel
import com.github.anddd7.order.repository.ReceiptRepository
import java.util.UUID

class ReceiptService(
    val receiptRepository: ReceiptRepository
) {
    fun find(id: UUID) = receiptRepository.findById(id).map { it.toModel() }
}
