package com.github.anddd7.security.service

import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.security.Principal

@Service
class UserService(
    passwordEncoder: PasswordEncoder,
    private val roleService: RoleService,
    private val permissionService: PermissionService
) : ReactiveUserDetailsService {
  private val log = LoggerFactory.getLogger(this.javaClass)

  private val users = listOf(
      User
          .withUsername("user")
          .password(passwordEncoder.encode("password"))
          .roles("USER")
          .build(),
      User
          .withUsername("admin")
          .password(passwordEncoder.encode("admin"))
          .roles("ADMIN")
          .build()
  )

  override fun findByUsername(username: String?) =
      Mono
          .fromCallable { users.find { it.username == username } }
          .doOnSuccess {
            log.debug("find user by name of [$username]")
          }

  @SuppressWarnings("SpreadOperator")
  fun getUserInfo(principal: Principal): Mono<UserDetails> {
    val roles = roleService.getRoles(principal.name)
    val permissions = permissionService.getPermissions(principal.name)

    return Mono.zip(roles, permissions)
        .map {
          User.withUsername(principal.name)
              .password("********")
              .roles(*it.t1.toTypedArray())
              .authorities(*it.t2.toTypedArray())
              .build()
        }
  }
}
