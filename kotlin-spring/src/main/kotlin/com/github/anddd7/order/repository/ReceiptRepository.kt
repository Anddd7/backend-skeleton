package com.github.anddd7.order.repository

import com.github.anddd7.order.repository.dao.ReceiptDAO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.UUID

@NoRepositoryBean
interface ReceiptRepository : JpaRepository<ReceiptDAO, UUID>
