package com.github.anddd7.order.repository

import com.github.anddd7.order.repository.dao.ProductDAO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductRepository : JpaRepository<ProductDAO, UUID>
