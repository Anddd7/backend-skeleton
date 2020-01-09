package com.github.anddd7.core.service

import io.mockk.every
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HelloServiceTest {

  private val helloService = HelloService()
  private val expectedTime = LocalDateTime.of(2020, 1, 1, 0, 0)

  @BeforeAll
  internal fun beforeAll() {
    mockkStatic("java.time.LocalDateTime")

    every { LocalDateTime.now() } returns expectedTime
  }


  @Test
  internal fun `should return hello`() {
    val result = helloService.getHello()

    assertThat(result).isEqualTo("Hello")
  }

  @Test
  internal fun `should return world`() {
    val result = helloService.getWorld()

    assertThat(result).isEqualTo("world")
  }

  @Test
  internal fun `should fetch current time when get current time successful`() {
    val result = helloService.getCurrentTime()

    StepVerifier.create(result)
        .expectNext(expectedTime)
        .verifyComplete()
  }
}
