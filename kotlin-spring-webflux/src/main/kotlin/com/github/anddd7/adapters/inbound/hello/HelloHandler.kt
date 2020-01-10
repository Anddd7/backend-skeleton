package com.github.anddd7.adapters.inbound.hello

import com.github.anddd7.adapters.ReactiveHandler
import com.github.anddd7.application.HelloStoryCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class HelloHandler(
    private val helloStoryCase: HelloStoryCase
) : ReactiveHandler {
  fun sayHello() = Mono.fromCallable(helloStoryCase::sayHello)
}
