package com.github.anddd7.security.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
  private val log = LoggerFactory.getLogger(this.javaClass)
  private val passwordEncoder = BCryptPasswordEncoder()

  @Bean
  fun webSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? =
      http.authorizeExchange()
          .anyExchange().authenticated()
          .and()
          .build()

  /**
   * mock in-memory user
   */
  @Bean
  fun userDetailsService(): MapReactiveUserDetailsService =
      MapReactiveUserDetailsService(
          User
              .withUsername("user")
              .password(passwordEncoder.encode("password"))
              .roles("USER")
              .build(),
          User
              .withUsername("admin")
              .password(passwordEncoder.encode("admin"))
              .roles("ADMIN")
              .build()
      )
}
