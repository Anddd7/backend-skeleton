package com.github.anddd7.model.auth

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "auth_user")
data class AuthUser(
    @Id
    @GeneratedValue
    @Column(name = "id")
    val id: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "role_id")
    val roleId: Long
) {
    /**
     * @JoinColumn indicates that this entity is the owner of the relationship
     * (that is: the corresponding table has a column with a foreign key to the referenced table)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    val role: AuthRole? = null
}