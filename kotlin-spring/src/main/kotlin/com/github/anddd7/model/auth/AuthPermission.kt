package com.github.anddd7.model.auth

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "auth_permission")
data class AuthPermission(
    @Id
    @GeneratedValue
    @Column(name = "id")
    val id: Long,

    @Column(name = "code")
    val code: String,

    @Column(name = "code")
    val description: String? = null
)