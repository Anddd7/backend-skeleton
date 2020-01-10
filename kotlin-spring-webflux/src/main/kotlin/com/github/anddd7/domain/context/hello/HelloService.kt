package com.github.anddd7.domain.context.hello

import com.github.anddd7.domain.Service
import java.time.LocalDateTime

class HelloService : Service {
  fun getHello() = "Hello"
  fun getWorld() = "world"
  fun getCurrentTime(): LocalDateTime = LocalDateTime.now()
}
