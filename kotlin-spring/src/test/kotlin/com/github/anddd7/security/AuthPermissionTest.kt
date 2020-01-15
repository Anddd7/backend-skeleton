package com.github.anddd7.security

import com.github.anddd7.security.model.AuthPermission
import com.github.anddd7.security.model.PermissionCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AuthPermissionTest {

    @Test
    fun isBelongTo() {
        assertThat(AuthPermission("DASHBOARD").belong(PermissionCode.DASHBOARD)).isTrue()
        assertThat(AuthPermission("ORDER").belong(PermissionCode.ORDER)).isTrue()

        assertThat(AuthPermission("Others").belong(PermissionCode.DASHBOARD)).isFalse()
    }
}
