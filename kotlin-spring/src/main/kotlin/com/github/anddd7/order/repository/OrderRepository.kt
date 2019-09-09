package com.github.anddd7.order.repository

import com.github.anddd7.order.repository.dao.OrderDAO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.UUID

@NoRepositoryBean
interface OrderRepository : JpaRepository<OrderDAO,UUID>
