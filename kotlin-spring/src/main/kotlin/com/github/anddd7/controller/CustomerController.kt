package com.github.anddd7.controller

import com.github.anddd7.service.auth.PermissionService
import org.springframework.web.bind.annotation.RestController

@RestController("/api")
class CustomerController(
    private val customerService: PermissionService
)