package com.github.anddd7.repository

import com.github.anddd7.model.auth.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthUserRepository : JpaRepository<AuthUser, Long> {
    fun findByName(name: String): AuthUser?
}
