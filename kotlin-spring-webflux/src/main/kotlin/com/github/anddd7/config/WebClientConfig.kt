package com.github.anddd7.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

  @Value("\${stock-api}")
  private lateinit var stockApi: String

  @Bean
  fun webClient() = WebClient.builder().baseUrl(stockApi).build()
}
