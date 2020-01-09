package com.github.anddd7.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/api/auth")
class UserController {

  @GetMapping
  fun currentUserName(principal: Mono<Principal>): Mono<String> = principal.map(Principal::getName)
}
