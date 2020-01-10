package com.github.anddd7.adapters.inbound

import com.github.anddd7.adapters.inbound.hello.HelloHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController(
    private val helloHandler: HelloHandler
) {
  @GetMapping
  fun hello() = helloHandler.sayHello()
}
