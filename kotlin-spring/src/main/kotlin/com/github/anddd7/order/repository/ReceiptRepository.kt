package com.github.anddd7.order.repository

import com.github.anddd7.order.repository.dao.ReceiptDAO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReceiptRepository : JpaRepository<ReceiptDAO, UUID>
