package com.github.anddd7.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DashboardController {

    @GetMapping("/dashboard")
    fun dashboard() = "dashboard"
}