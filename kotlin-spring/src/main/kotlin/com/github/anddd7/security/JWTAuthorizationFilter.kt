package com.github.anddd7.security

import com.github.anddd7.core.configuration.JsonUtil
import com.github.anddd7.security.api.dto.AuthorizedUser
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Authorization is 授权. It's used to allow user to do something.
 */
class JWTAuthorizationFilter(
    private val jwtConfig: JWTConfig,
    manager: AuthenticationManager
) : BasicAuthenticationFilter(manager) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        authenticate(request)?.let {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(request, response)
    }

    private fun authenticate(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(JWTConfig.TOKEN_HEADER)
        if (token.isNullOrEmpty() || !token.startsWith(JWTConfig.TOKEN_PREFIX)) return null
        log.debug("Extract JWT token from header: {}", token)

        val parsedToken = Jwts.parser()
            .setSigningKey(jwtConfig.keyPair.publicKey)
            .parseClaimsJws(token.replace("Bearer ", ""))
        val body = parsedToken.body

        val user = JsonUtil.Global.readValue(body["user"].toString(), AuthorizedUser::class.java)
        val authorities = user.permissions.map { GrantedAuthority { it } }
        log.debug("Successful authorized: {}, authorities: {}", user.username, authorities)

        return UsernamePasswordAuthenticationToken(user.username, null, authorities)
    }
}
