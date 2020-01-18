package com.github.anddd7

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@EnableApiTest
@AutoConfigureWebTestClient
internal class ProductCoroutineControllerApiTest {

  @Autowired
  private lateinit var webClient: WebTestClient

  @Test
  internal fun `should find all products`() {
    webClient.get().uri("/coroutine/product").exchange().expectStatus().isOk
  }

  @Test
  internal fun `should get the product by id`() {
    webClient.get().uri("/coroutine/product/1").exchange().expectStatus().isOk
  }

  @Test
  internal fun `should get stock of product`() {
    webClient.get().uri("/coroutine/product/1/stock").exchange().expectStatus().isOk
  }
}
