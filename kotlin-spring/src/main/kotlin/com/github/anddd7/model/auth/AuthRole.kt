package com.github.anddd7.model.auth

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.github.anddd7.repository.refs.AuthRolePermission
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "auth_role")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
data class AuthRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "name")
    val name: String = ""
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: AuthRole? = null

    @OneToMany(mappedBy = "parent")
    val children: Set<AuthRole> = emptySet()

    /**
     * @OneToMany(mappedBy=...) indicates that the entity in this side is the inverse of the relationship,
     * and the owner resides in the "other" entity
     */
    @OneToMany(mappedBy = "role")
    val users: Set<AuthUser> = emptySet()

    // @ManyToMany https://www.baeldung.com/jpa-many-to-many
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    val permissionRefs: Set<AuthRolePermission> = emptySet()

    @delegate:Transient
    private val parentPermissions: List<AuthPermission> by lazy { parent?.permissions ?: emptyList() }

    @delegate:Transient
    val permissions: List<AuthPermission> by lazy {
        (parentPermissions + permissionRefs.mapNotNull { it.permission }).distinctBy { it.code }
    }

    fun isAccessible(code: PermissionCode) = permissions.any { it.code == code.name }
}
