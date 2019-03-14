package com.github.anddd7.model.auth

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "auth_user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
data class AuthUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "name")
    val name: String = ""
) {
    /**
     * @JoinColumn indicates that this entity is the owner of the relationship
     * (that is: the corresponding table has a column with a foreign key to the referenced table)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    val role: AuthRole? = null

    @delegate:Transient
    val permissions: List<AuthPermission> by lazy { role?.permissions ?: emptyList() }
}