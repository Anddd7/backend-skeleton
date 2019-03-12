package com.github.anddd7.repository

import com.github.anddd7.SQLScript
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
@Sql(scripts = [SQLScript.AUTH_USER, SQLScript.AUTH_ROLE])
internal class AuthUserRoleRepositoryTest {
    @Autowired
    private lateinit var authUserRepository: AuthUserRepository

    @Test
    fun `should return all users and related roles`() {
        val users = authUserRepository.findAll()
        assertThat(users).hasSize(4)

        val roles = users.mapNotNull { it.role }.distinctBy { it.id }.sortedBy { it.id }
        assertThat(roles).hasSize(3)
        assertThat(roles.first().name).isEqualTo("ROLE1")
    }

    @Autowired
    private lateinit var authRoleRepository: AuthRoleRepository

    @Test
    fun `should return all roles and related users`() {
        val roles = authRoleRepository.findAll()
        assertThat(roles).hasSize(3)

        val users = roles.flatMap { it.users }.distinctBy { it.id }.sortedBy { it.id }
        assertThat(users).hasSize(4)
        assertThat(users.first().name).isEqualTo("USER1")
    }

    @Test
    fun `should return roles with its parent and children`() {
        val role = authRoleRepository.getOne(2)

        val parent = role.parent!!
        assertThat(parent.name).isEqualTo("ROLE1")

        val children = parent.children
        assertThat(children).hasSize(2)
        assertThat(children).contains(role)
    }
}