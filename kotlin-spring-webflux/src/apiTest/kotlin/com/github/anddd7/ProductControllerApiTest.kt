package com.github.anddd7

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TestApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
//@FlywayTest
internal class ProductControllerApiTest {

  @Autowired
  private lateinit var webClient: WebTestClient

  @Test
  internal fun `should find all products`() {
    webClient.get().uri("/product").exchange().expectStatus().isOk
  }

  @Test
  internal fun `should get the product by id`() {
    webClient.get().uri("/product/1").exchange().expectStatus().isOk
  }

  @Test
  internal fun `should get stock of product`() {
    webClient.get().uri("/product/1/stock").exchange().expectStatus().isOk
  }
}
