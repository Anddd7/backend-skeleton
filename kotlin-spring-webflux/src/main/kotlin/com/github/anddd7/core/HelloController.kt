package com.github.anddd7.core

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal
import java.util.stream.Collectors

@RestController
@RequestMapping
class HelloController {

  private val log = LogFactory.getLog(this.javaClass)

  @RequestMapping("/hello")
  fun hello(principal: Mono<Principal>): Mono<String> =
      Flux
          .concat(
              Mono.just("Hello"),
              principal.map { it.name }
          )
          .collect(Collectors.joining(", "))
          .doOnSuccess(log::debug)
}
