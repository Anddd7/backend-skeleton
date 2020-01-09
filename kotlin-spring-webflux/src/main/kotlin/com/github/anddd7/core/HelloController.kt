package com.github.anddd7.core

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@RestController
@RequestMapping
class HelloController {

  private val log = LogFactory.getLog(this.javaClass)
  private val delay: Long = 1000

  @RequestMapping("/hello")
  fun hello(): Mono<String> {

    return Flux
        .merge(
            Mono.just("Hello").doOnNext { Thread.sleep(delay) },
            Mono.just("world").doOnNext { Thread.sleep(delay) }
        )
        .collect(Collectors.joining(", "))
        .doOnSuccess {
          log.debug(it)
        }
  }
}
