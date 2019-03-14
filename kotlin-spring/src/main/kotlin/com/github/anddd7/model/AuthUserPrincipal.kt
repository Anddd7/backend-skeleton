package com.github.anddd7.model

import com.github.anddd7.model.auth.AuthUser
import org.springframework.security.core.userdetails.UserDetails

data class AuthUserPrincipal(private val authUser: AuthUser) : UserDetails {

    override fun getUsername() = authUser.name
    override fun getPassword() = "{noop}${authUser.name}"
    override fun getAuthorities() = authUser.permissions

    fun getRole() = authUser.role

    override fun isEnabled() = true
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
}