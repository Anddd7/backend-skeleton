package com.github.anddd7.repository

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureEmbeddedDatabase
@Sql(scripts = ["classpath:db/fixture/auth_role.test.sql", "classpath:db/fixture/auth_user.test.sql"])
internal class AuthUserRepositoryTest {
    @Autowired
    private lateinit var authUserRepository: AuthUserRepository

    @Test
    fun `should return all users`() {
        val users = authUserRepository.findAll()
        assertThat(users).hasSize(4)

        val firstUser = users.first()
        assertThat(firstUser.name).isEqualTo("USER1")

        val role = firstUser.role!!
        assertThat(role.name).isEqualTo("ROLE1")

        val usersOfRole = role.users
        assertThat(usersOfRole).hasSize(2)
    }
}