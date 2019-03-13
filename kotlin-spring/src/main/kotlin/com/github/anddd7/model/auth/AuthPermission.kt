package com.github.anddd7.model.auth

import com.github.anddd7.repository.refs.AuthRolePermission
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "auth_permission")
class AuthPermission(
    @Id
    @Column(name = "code")
    val code: String = "",

    @Column(name = "description")
    val description: String? = null
) {
    @OneToMany(mappedBy = "permission")
    val roleRefs: Set<AuthRolePermission> = emptySet()

    // use lazy load instead of repeated function calls
    @delegate:Transient
    val roles: List<AuthRole> by lazy {
        roleRefs.mapNotNull { it.role }.flatMap { it.children + it }.distinctBy { it.id }
    }
    @delegate:Transient
    val users: List<AuthUser> by lazy { roles.flatMap { it.users }.distinctBy { it.id } }
}

enum class PermissionCode {
    DASHBOARD, ORDER
}