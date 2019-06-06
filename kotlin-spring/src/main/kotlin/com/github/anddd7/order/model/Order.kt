package com.github.anddd7.order.model

import com.github.anddd7.order.api.OrderDTO
import com.github.anddd7.order.repository.dao.OrderDAO
import com.github.anddd7.order.service.ProductService
import java.util.UUID

data class Order(
    val id: UUID,
    val products: List<Product>,
    val receipt: Receipt?
)

class OrderMapper(
    private val productService: ProductService,
    private val productMapper: ProductMapper,
    private val receiptMapper: ReceiptMapper
) : ModelMapper<OrderDTO, Order, OrderDAO> {
    override fun toDTO(model: Order) = model.run {
        OrderDTO(
            products = productMapper.toDTO(products),
            receipt = receipt?.let(receiptMapper::toDTO)
        )
    }

    override fun toDAO(model: Order): OrderDAO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromDTO(dto: OrderDTO): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromDAO(dao: OrderDAO) = dao.run {
        Order(
            id = id,
            products = productService.find(productIds),
            receipt = receipt?.let(receiptMapper::fromDAO)
        )
    }
}
