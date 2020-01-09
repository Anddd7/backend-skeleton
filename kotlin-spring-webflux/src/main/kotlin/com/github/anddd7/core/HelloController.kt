package com.github.anddd7.core

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@RestController
@RequestMapping
class HelloController {

  private val log = LogFactory.getLog(this.javaClass)

  @GetMapping("/hello")
  fun hello(): Mono<String> = Flux
      .merge(
          Mono.just("Hello"),
          Mono.just("world")
      )
      .collect(Collectors.joining(", "))
      .doOnSuccess(log::debug)
}
