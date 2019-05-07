package com.github.anddd7.security.repository

import com.github.anddd7.security.model.AuthRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRoleRepository : JpaRepository<AuthRole, Long>
