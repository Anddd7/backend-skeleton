package com.github.anddd7.controller

import com.github.anddd7.model.AuthUserPrincipal
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthorizationController(
    private val authenticationManager: AuthenticationManager
) {
    @GetMapping("/login")
    fun login(@RequestParam name: String): AuthUserPrincipalDTO {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(name, name)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val principal = authentication.principal as AuthUserPrincipal
        return principal.toDTO()
    }
}

fun AuthUserPrincipal.toDTO(): AuthUserPrincipalDTO =
    AuthUserPrincipalDTO(username, getRole()?.name, authorities.map { it.authority })

data class AuthUserPrincipalDTO(
    val name: String,
    val role: String?,
    val permissions: List<String>
)
