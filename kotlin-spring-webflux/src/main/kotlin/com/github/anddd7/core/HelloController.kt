package com.github.anddd7.core

import com.github.anddd7.core.service.HelloService
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping
class HelloController(
    private val helloService: HelloService
) {
  private val log = LogFactory.getLog(this.javaClass)

  @GetMapping("/hello")
  fun hello(): String {
    val currentTime = helloService.getCurrentTime().block()
    val response = "${helloService.getHello()}, ${helloService.getWorld()}. (${currentTime})"

    return response.also(log::debug)
  }

  @GetMapping("/reactive/hello")
  fun helloWithReactive(): Mono<String> {
    val hello = Mono.just(helloService.getHello())
    val world = Mono.just(helloService.getWorld())
    val currentTime = helloService.getCurrentTime()

    return Mono
        .zip(hello, world, currentTime)
        .map { "${it.t1}, ${it.t2}. (${it.t3})" }
        .doOnSuccess(log::debug)
  }
}
