package com.github.anddd7.security.service

import com.github.anddd7.security.repository.UserRepository
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
    private val permissionService: PermissionService,
    private val userRepository: UserRepository
) : ReactiveUserDetailsService {
  private val log = LoggerFactory.getLogger(this.javaClass)

  private val users: List<UserDetails> by lazy {
    listOf(
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
  }

  override fun findByUsername(username: String?) =
      Mono
          .fromCallable { users.find { it.username == username } }
          .doOnSuccess {
            log.debug("find user by name of [$username]")
          }

  @SuppressWarnings("SpreadOperator")
  fun getUserInfo(principal: Principal): Mono<UserDetails> {
    log.debug("receive the request")

    val user = Mono.fromCallable {
      log.debug("call repository")

      userRepository.findAll().first()
    }
    val roles = roleService.getRoles(principal.name)
    val permissions = permissionService.getPermissions(principal.name)

    log.debug("return the mono of user")

    return Mono.zip(user, roles, permissions)
        .map {
          User.withUsername(it.t1.name)
              .password("********")
              .roles(*it.t2.toTypedArray())
              .authorities(*it.t3.toTypedArray())
              .build()
        }
  }
}
