package com.github.anddd7.security.repository

import com.github.anddd7.security.model.AuthPermission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthPermissionRepository : JpaRepository<AuthPermission, String>
