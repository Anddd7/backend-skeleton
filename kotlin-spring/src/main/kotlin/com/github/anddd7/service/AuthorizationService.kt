package com.github.anddd7.service

import com.github.anddd7.model.AuthUserPrincipal
import com.github.anddd7.repository.AuthUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthorizationService(val authUserRepository: AuthUserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val authUser = authUserRepository.findByName(username)
            ?: throw UsernameNotFoundException("Can't find registered user: $username")
        return AuthUserPrincipal(authUser)
    }
}
