package com.github.anddd7.security

import com.github.anddd7.security.api.dto.AuthorizedUser
import com.github.anddd7.security.model.AuthUserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class TempAuthController {

    @GetMapping("/type")
    fun type(): String {
        val authentication = SecurityContextHolder.getContext().authentication ?: return "Not Authenticated"
        if (authentication.principal is AuthUserPrincipal) return "Authenticated"
        if (authentication.principal is AuthorizedUser) return "Authorized"
        return "Unknown"
    }
}