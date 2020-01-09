package com.github.anddd7.core;

import com.github.anddd7.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = Application.class)
@WebFluxTest(HelloController.class)
public class HelloControllerTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  @WithMockUser
  void should_say_hello() {
    webClient.get().uri("/hello")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .isEqualTo("Hello, user");
  }
}
