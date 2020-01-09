package com.github.anddd7.core

import com.github.anddd7.TestApplication
import com.github.anddd7.core.service.HelloService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@ContextConfiguration(classes = [TestApplication::class])
@WebFluxTest(HelloController::class)
internal class HelloControllerTest {

  @Autowired
  private lateinit var webClient: WebTestClient

  @MockkBean
  private lateinit var helloService: HelloService

  private val expectedTime = LocalDateTime.of(2020, 1, 1, 0, 0)

  @Test
  fun `should say hello to me`() {
    every { helloService.getHello() } returns "Hello"
    every { helloService.getWorld() } returns "world"
    every { helloService.getCurrentTime() }.returns(Mono.just(expectedTime))

    webClient.get().uri("/hello")
        .exchange()
        .expectStatus().isOk
        .expectBody<String>()
        .isEqualTo("Hello, world. ($expectedTime)")
  }

  @Test
  fun `should say hello to me in reactive way`() {
    every { helloService.getHello() } returns "Hello"
    every { helloService.getWorld() } returns "world"
    every { helloService.getCurrentTime() }.returns(Mono.just(expectedTime))

    webClient.get().uri("/reactive/hello")
        .exchange()
        .expectStatus().isOk
        .expectBody<String>()
        .isEqualTo("Hello, world. ($expectedTime)")
  }
}
