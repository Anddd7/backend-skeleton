package com.github.anddd7.repository

import com.github.anddd7.SQLScript.AUTH_PERMISSION
import com.github.anddd7.SQLScript.AUTH_ROLE
import com.github.anddd7.model.auth.PermissionCode
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
@Sql(scripts = [AUTH_ROLE, AUTH_PERMISSION])
internal class AuthRolePermissionRepositoryTest {
    @Autowired
    private lateinit var authRoleRepository: AuthRoleRepository

    @Test
    fun `should return all roles and related permissions`() {
        val roles = authRoleRepository.findAll()
        assertThat(roles).hasSize(3)

        val permissions = roles.flatMap { it.permissions }.distinctBy { it.id }.sortedBy { it.id }
        assertThat(permissions).hasSize(2)
        assertThat(permissions.first().code).isEqualTo("DASHBOARD")
    }

    @Test
    fun `should return whether role have the permission`() {
        val role = authRoleRepository.getOne(1)
        assertThat(role.isAccessible(PermissionCode.DASHBOARD)).isTrue()
        assertThat(role.isAccessible(PermissionCode.ORDER)).isFalse()
    }

    @Autowired
    private lateinit var authPermissionRepository: AuthPermissionRepository

    @Test
    fun `should return all permissions and related roles`() {
        val permissions = authPermissionRepository.findAll()
        assertThat(permissions).hasSize(2)

        val roles = permissions.flatMap { it.roles }.distinctBy { it.id }.sortedBy { it.id }
        assertThat(roles).hasSize(3)
        assertThat(roles.first().name).isEqualTo("ROLE1")
    }
}