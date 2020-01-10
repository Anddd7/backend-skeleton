package com.github.anddd7

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class Application

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
