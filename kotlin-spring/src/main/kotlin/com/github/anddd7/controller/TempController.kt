package com.github.anddd7.controller

import com.github.anddd7.service.TempService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/temp")
class TempController(val tempService: TempService) {

    @GetMapping("/version")
    fun version() = tempService.getVersion()

    @GetMapping("/ping")
    fun ping() = mapOf(
        "version" to version(),
        "active" to "ok"
    )
}