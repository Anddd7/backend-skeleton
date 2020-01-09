package com.github.anddd7.security

import com.github.anddd7.security.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/api/auth")
class UserController(private val userService: UserService) {

  @GetMapping
  fun currentUserName(principal: Mono<Principal>): Mono<String> = principal.map(Principal::getName)

  @GetMapping("/user")
  fun currentUser(principal: Mono<Principal>): Mono<UserDetails> = principal.flatMap(userService::getUserInfo)
}
