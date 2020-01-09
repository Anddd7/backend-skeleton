package com.github.anddd7

import com.github.anddd7.security.config.SecurityConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(value = [SecurityConfig::class])
internal class TestApplication
