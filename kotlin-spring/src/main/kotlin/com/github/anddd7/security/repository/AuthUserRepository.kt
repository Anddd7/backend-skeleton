package com.github.anddd7.security.repository

import com.github.anddd7.security.model.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthUserRepository : JpaRepository<AuthUser, Long> {
    fun findByName(name: String): AuthUser?
}
