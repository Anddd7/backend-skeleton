package com.github.anddd7.security

import com.github.anddd7.TestApplication
import com.github.anddd7.security.service.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

@ContextConfiguration(classes = [TestApplication::class])
@WebFluxTest(UserController::class)
internal class UserControllerTest {

  @Autowired
  private lateinit var webClient: WebTestClient

  @MockkBean
  private lateinit var userService: UserService

  @Test
  @WithMockUser
  fun `should return user name when access with mock user`() {
    webClient.get().uri("/api/auth")
        .exchange()
        .expectStatus().isOk
        .expectBody<String>()
        .isEqualTo("user")
  }

  @Test
  @WithAnonymousUser
  fun `should got 401 when access with anonymous user`() {
    webClient.get().uri("/api/auth")
        .exchange()
        .expectStatus().isForbidden
  }

  @Test
  @WithMockUser
  fun `should return user info when access with mock user`() {
    every { userService.getUserInfo(any()) } returns Mono.just(
        User.withUsername("user")
            .password("********")
            .authorities("A", "B", "C")
            .build()
    )

    webClient.get().uri("/api/auth/user")
        .exchange()
        .expectStatus().isOk
        .expectBody()
        .jsonPath("$.username").isEqualTo("user")
        .jsonPath("$.password").isEqualTo("********")
        .jsonPath("$.authorities").isArray
  }
}

