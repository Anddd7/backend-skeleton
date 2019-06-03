package com.github.anddd7.order.repository

import com.github.anddd7.order.repository.dao.OrderDAO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderRepository : JpaRepository<OrderDAO,UUID>
