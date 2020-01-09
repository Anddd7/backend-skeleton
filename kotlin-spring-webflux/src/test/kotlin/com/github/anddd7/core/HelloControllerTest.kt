package com.github.anddd7.core

import com.github.anddd7.TestApplication
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ContextConfiguration(classes = [TestApplication::class])
@WebFluxTest(HelloController::class)
class HelloControllerTest {

  @Autowired
  private lateinit var webClient: WebTestClient

  @Test
  fun `should say hello to me`() {
    webClient.get().uri("/hello")
        .exchange()
        .expectStatus().isOk
        .expectBody<String>()
        .isEqualTo("Hello, world")
  }
}
