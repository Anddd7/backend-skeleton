package com.github.anddd7.core.api

import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class OrderController {

    @GetMapping("/order")
    @Secured("ORDER")
    fun order() = "order"
}
