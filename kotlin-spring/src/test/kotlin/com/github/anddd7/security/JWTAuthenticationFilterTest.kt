package com.github.anddd7.security

import com.github.anddd7.security.model.AuthPermission
import com.github.anddd7.security.model.AuthRole
import com.github.anddd7.security.model.AuthUserPrincipal
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

internal class JWTAuthenticationFilterTest {

    private val jwtConfig = JWTConfig()
    private val authenticationManager = mockk<AuthenticationManager>()

    @Test
    fun `should return JWT while successful authenticated`() {
        val principal = mockk<AuthUserPrincipal> {
            every { this@mockk.username } returns "TestUser"
            every { this@mockk.getRole() } returns AuthRole(id = -1, name = "TestRole")
            every { this@mockk.authorities } returns listOf(AuthPermission("TEST", "ONLY for testing"))
        }
        every { authenticationManager.authenticate(any()) } answers {
            val authentication = firstArg<Authentication>()
            UsernamePasswordAuthenticationToken(principal, authentication.credentials)
        }

        val mockMvc = standaloneSetup(TempAuthController())
            .addFilter<StandaloneMockMvcBuilder>(
                JWTAuthenticationFilter(jwtConfig, authenticationManager),
                jwtConfig.authUrl
            )
            .build()

        mockMvc.perform(
            get(jwtConfig.authUrl)
                .param("username", "username")
                .param("password", "password")
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(header().exists(JWTConfig.TOKEN_HEADER))


        mockMvc.perform(
            get("/type")
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().string("Authenticated"))
    }

    @Test
    fun `should return 401 while failed authenticated `() {
        every { authenticationManager.authenticate(any()) } throws AuthenticationServiceException("Authenticate failed")

        val mockMvc = standaloneSetup(TempAuthController())
            .addFilter<StandaloneMockMvcBuilder>(
                JWTAuthenticationFilter(jwtConfig, authenticationManager),
                jwtConfig.authUrl
            )
            .build()

        mockMvc.perform(get(jwtConfig.authUrl))
            .andExpect(status().is4xxClientError)
            .andExpect(header().doesNotExist(JWTConfig.TOKEN_HEADER))


        mockMvc.perform(get("/type"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().string("Not Authenticated"))
    }
}
