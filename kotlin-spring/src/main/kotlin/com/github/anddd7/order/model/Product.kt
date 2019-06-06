package com.github.anddd7.order.model

import com.github.anddd7.order.api.ProductDTO
import com.github.anddd7.order.repository.dao.ProductDAO
import java.math.BigDecimal
import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val price: BigDecimal
)

class ProductMapper : ModelMapper<ProductDTO, Product, ProductDAO> {
    override fun toDTO(model: Product) = model.run { ProductDTO(name, price) }

    override fun toDAO(model: Product): ProductDAO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromDTO(dto: ProductDTO): Product {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromDAO(dao: ProductDAO) = dao.run { Product(id, name, price) }
}
