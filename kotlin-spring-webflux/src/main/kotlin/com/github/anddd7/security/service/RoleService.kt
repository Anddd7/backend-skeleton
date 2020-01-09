package com.github.anddd7.security.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class RoleService {
  fun getRoles(name: String): Mono<List<String>> {
    return Mono.just(listOf("A", "B", "C")).delayElement(Duration.ofSeconds(5))
  }
}
