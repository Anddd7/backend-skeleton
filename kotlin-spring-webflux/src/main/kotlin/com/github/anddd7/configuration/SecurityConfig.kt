package com.github.anddd7.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
  @Bean
  fun passwordEncoder() = BCryptPasswordEncoder()

  @Bean
  fun webSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
      http
          .csrf().disable()
          .authorizeExchange()
          .pathMatchers("/api/*").authenticated()
          .anyExchange().permitAll()
          .and()
          .httpBasic(Customizer.withDefaults())
          .build()

  @Bean
  fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService =
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
