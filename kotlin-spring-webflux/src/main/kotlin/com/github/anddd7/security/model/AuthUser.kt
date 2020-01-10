package com.github.anddd7.security.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "auth_user")
data class AuthUser(
    @Id
    val id: Long = 0,

    @Column(name = "name")
    val name: String
)
