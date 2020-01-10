package com.github.anddd7.application

import com.github.anddd7.domain.context.hello.HelloService
import org.springframework.stereotype.Service

@Service
class HelloStoryCase : StoryCase {
  private val helloService = HelloService()

  fun sayHello(): String {
    val hello = helloService.getHello()
    val world = helloService.getWorld()
    val currentTime = helloService.getCurrentTime()

    return "$hello, $world. ($currentTime)"
  }
}
