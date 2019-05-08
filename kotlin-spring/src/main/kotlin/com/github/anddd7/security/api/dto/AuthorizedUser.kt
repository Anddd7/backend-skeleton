package com.github.anddd7.security.api.dto

import com.github.anddd7.security.model.AuthUserPrincipal

data class AuthorizedUser(
    val username: String,
    val role: String?,
    val permissions: List<String>
)

fun AuthUserPrincipal.toAuthenticatedUser() = AuthorizedUser(
    username,
    getRole()?.name,
    authorities.map { it.code }
)
