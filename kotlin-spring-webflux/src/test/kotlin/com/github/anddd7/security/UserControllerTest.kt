package com.github.anddd7.security

import com.github.anddd7.TestApplication
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ContextConfiguration(classes = [TestApplication::class])
@WebFluxTest(UserController::class)
class UserControllerTest {

  @Autowired
  private lateinit var webClient: WebTestClient

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
}

