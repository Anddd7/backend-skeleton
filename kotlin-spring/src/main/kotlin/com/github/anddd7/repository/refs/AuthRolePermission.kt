package com.github.anddd7.repository.refs

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Embeddable
data class AuthRolePermissionKey(
    @Column(name = "role_id")
    val roleId: Long,

    @Column(name = "permission_id")
    val permissionId: Long
)

@Entity
@Table(name = "auth_role_permission")
data class AuthRolePermission(
    @EmbeddedId
    val id: AuthRolePermissionKey,

    @Column(name = "expired_date")
    val expiredDate: LocalDateTime? = null
)