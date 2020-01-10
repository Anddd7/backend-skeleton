package com.github.anddd7.security.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class PermissionService {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getPermissions(name: String): Mono<List<String>> {
    log.debug("call get permissions")

    return Mono.just(listOf("P-A", "P-B", "P-C"))
        .delayElement(Duration.ofSeconds(5))
        .doOnNext {
          log.debug("return the roles")
        }
  }
}
