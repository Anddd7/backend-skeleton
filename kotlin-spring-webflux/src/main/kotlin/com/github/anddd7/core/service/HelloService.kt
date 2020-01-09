package com.github.anddd7.core.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class HelloService {
  fun getHello() = "Hello"
  fun getWorld() = "world"
  fun getCurrentTime(): Mono<LocalDateTime> = Mono.fromCallable { LocalDateTime.now() }
}
