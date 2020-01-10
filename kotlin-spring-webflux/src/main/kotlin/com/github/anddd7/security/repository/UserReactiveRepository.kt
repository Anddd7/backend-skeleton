package com.github.anddd7.security.repository

import com.github.anddd7.security.model.AuthUser
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserReactiveRepository(val userRepository: UserRepository) {
  @Transactional(rollbackOn = [java.lang.RuntimeException::class])
  fun saveAndFailed(authUser: AuthUser): AuthUser {
    userRepository.save(authUser).also {
      throw RuntimeException()
    }
  }
}
