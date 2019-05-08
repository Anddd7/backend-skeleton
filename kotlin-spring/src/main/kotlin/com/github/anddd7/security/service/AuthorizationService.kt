package com.github.anddd7.security.service

import com.github.anddd7.security.model.AuthUserPrincipal
import com.github.anddd7.security.repository.AuthUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthorizationService(val authUserRepository: AuthUserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val authUser = authUserRepository.findByName(username)
            ?: throw UsernameNotFoundException("Can't find registered user: $username")
        return AuthUserPrincipal(authUser)
    }
}
