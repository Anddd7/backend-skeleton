package com.github.anddd7.model.auth

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
data class AuthRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "name")
    val name: String = ""
) {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: AuthRole? = null

    @OneToMany(mappedBy = "parent")
    val children: Set<AuthRole> = emptySet()

    /**
     * @OneToMany(mappedBy=...) indicates that the entity in this side is the inverse of the relationship,
     * and the owner resides in the "other" entity
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    val users: Set<AuthUser> = emptySet()
}