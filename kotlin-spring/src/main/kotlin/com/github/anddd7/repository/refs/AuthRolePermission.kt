package com.github.anddd7.repository.refs

import com.github.anddd7.model.auth.AuthPermission
import com.github.anddd7.model.auth.AuthRole
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Embeddable
data class AuthRolePermissionKey(
    @Column(name = "role_id")
    val roleId: Long = 0,

    @Column(name = "permission_code")
    val permissionCode: String = ""
) : Serializable

@Entity
@Table(name = "auth_role_permission")
data class AuthRolePermission(
    @EmbeddedId
    val id: AuthRolePermissionKey = AuthRolePermissionKey(),

    @Column(name = "expired_date")
    val expiredDate: LocalDateTime? = null
) {
    /**
     * @MapsId extract field included in embedded id is a foreign key
     * @JoinColumn then mark it as a join column of @ManyToOne relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permission_code")
    @JoinColumn(name = "permission_code")
    val permission: AuthPermission? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    val role: AuthRole? = null
}
