package com.github.anddd7.security

import com.github.anddd7.core.configuration.JsonUtil
import com.github.anddd7.security.api.dto.toAuthenticatedUser
import com.github.anddd7.security.model.AuthUserPrincipal
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Authentication is 验证. It's used to recognize user and know who login.
 */
class JWTAuthenticationFilter(
    private val jwtConfig: JWTConfig,
    manager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    init {
        setPostOnly(false)
        setFilterProcessesUrl(jwtConfig.authUrl)
        authenticationManager = manager
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        return super.attemptAuthentication(request, response)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = authResult.principal as AuthUserPrincipal
        log.debug("Successful authenticated: {}, authorities: {}", user.username, user.authorities)

        val token = buildToken(user)
        log.debug("Generate JWT token {}", token)

        SecurityContextHolder.getContext().authentication = authResult
        response.addHeader(JWTConfig.TOKEN_HEADER, "Bearer $token")
    }

    private fun buildToken(user: AuthUserPrincipal): String {
        val authenticatedUser = user.toAuthenticatedUser()
        val claims = mapOf(
            "user" to JsonUtil.Global.writeValueAsString(authenticatedUser)
        )
        log.debug("Mapping claims: {}", claims)

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setAudience("kotlin-spring")
            .setIssuer("kotlin-spring-security")
            .signWith(SignatureAlgorithm.forName(jwtConfig.algorithm), jwtConfig.keyPair.privateKey)
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expirationTime))
            .setSubject(user.username)
            .addClaims(claims)
            .compact()
    }
}
