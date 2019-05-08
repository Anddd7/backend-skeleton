package com.github.anddd7.security.api

import com.github.anddd7.security.api.dto.AuthorizedUser
import com.github.anddd7.security.api.dto.toAuthenticatedUser
import com.github.anddd7.security.model.AuthUserPrincipal
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder.getContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthorizationController(
    private val authenticationManager: AuthenticationManager
) {
    @Deprecated("Use `/auth/authenticate` instead")
    @GetMapping("/login")
    fun login(@RequestParam name: String): AuthorizedUser {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(name, name)
        )
        getContext().authentication = authentication
        val principal = authentication.principal as AuthUserPrincipal
        return principal.toAuthenticatedUser()
    }
}
