package com.github.anddd7.security.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class RoleService {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getRoles(name: String): Mono<List<String>> {
    log.debug("call get roles")

    return Mono.just(listOf("A", "B", "C"))
        .delayElement(Duration.ofSeconds(5))
        .doOnNext {
          log.debug("return the roles")
        }
  }
}
