package com.github.anddd7.security.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class PermissionService {
  fun getPermissions(name: String): Mono<List<String>> {
    return Mono.just(listOf("P-A", "P-B", "P-C")).delayElement(Duration.ofSeconds(5))
  }
}
