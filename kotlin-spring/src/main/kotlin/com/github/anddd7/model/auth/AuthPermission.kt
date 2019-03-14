package com.github.anddd7.model.auth

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.github.anddd7.repository.refs.AuthRolePermission
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "auth_permission")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
class AuthPermission(
    @Id
    @Column(name = "code")
    val code: String = "",

    @Column(name = "description")
    val description: String? = null
) : GrantedAuthority {
    @OneToMany(mappedBy = "permission")
    @JsonIgnore
    val roleRefs: Set<AuthRolePermission> = emptySet()

    // use lazy load instead of repeated function calls
    @delegate:Transient
    val roles: List<AuthRole> by lazy {
        roleRefs.mapNotNull { it.role }.flatMap { it.children + it }.distinctBy { it.id }
    }
    @delegate:Transient
    val users: List<AuthUser> by lazy { roles.flatMap { it.users }.distinctBy { it.id } }

    override fun getAuthority() = code

    fun belong(permissionCode: PermissionCode) = code === permissionCode.name
}

enum class PermissionCode {
    DASHBOARD, ORDER
}