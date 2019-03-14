package com.github.anddd7.repository

import com.github.anddd7.model.auth.AuthRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRoleRepository : JpaRepository<AuthRole, Long>
