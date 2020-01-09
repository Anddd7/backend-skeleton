package com.github.anddd7.security.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.security.Principal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class UserServiceTest {

  @MockK
  private lateinit var passwordEncoder: PasswordEncoder
  @MockK
  private lateinit var roleService: RoleService
  @MockK
  private lateinit var permissionService: PermissionService

  @InjectMockKs
  private lateinit var userService: UserService

  @BeforeAll
  internal fun beforeAll() {
    every { passwordEncoder.encode(any()) } answers { firstArg() }
  }

  @Test
  fun `should get user info`() {
    every { roleService.getRoles(any()) } returns Mono.just(emptyList())
    every { permissionService.getPermissions(any()) } returns Mono.just(emptyList())
    val principle = mockk<Principal> {
      every { name } returns "user"
    }

    val result = userService.getUserInfo(principle)

    StepVerifier.create(result)
        .assertNext { it.username == "user" }
        .verifyComplete()
  }
}
