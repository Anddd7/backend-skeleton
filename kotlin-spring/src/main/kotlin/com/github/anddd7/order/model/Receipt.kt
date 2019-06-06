package com.github.anddd7.order.model

import com.github.anddd7.order.api.ReceiptDTO
import com.github.anddd7.order.repository.dao.ReceiptDAO
import java.math.BigDecimal
import java.util.UUID

data class Receipt(
    val id: UUID,
    val itemNumber: Int,
    val totalPrice: BigDecimal
)

class ReceiptMapper : ModelMapper<ReceiptDTO, Receipt, ReceiptDAO> {
    override fun toDTO(model: Receipt) = model.run {
        ReceiptDTO(itemNumber, totalPrice)
    }

    override fun toDAO(model: Receipt): ReceiptDAO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromDTO(dto: ReceiptDTO): Receipt {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromDAO(dao: ReceiptDAO) = dao.run {
        Receipt(id, itemNumber, totalPrice)
    }
}
