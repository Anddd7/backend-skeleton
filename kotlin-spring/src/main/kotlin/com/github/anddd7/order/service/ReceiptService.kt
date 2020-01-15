package com.github.anddd7.order.service

import com.github.anddd7.order.model.ReceiptMapper
import com.github.anddd7.order.repository.ReceiptRepository
import java.util.UUID

class ReceiptService(
    private val receiptRepository: ReceiptRepository,
    private val receiptMapper: ReceiptMapper
) {
  fun find(id: UUID) = receiptMapper.fromDAO(
      receiptRepository.findById(id).orElseThrow { NotFoundException("Not found receipt id = $id") }
  )
}
