package com.github.anddd7.service

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class EnvironmentProvider(val environment:Environment){
    fun notProduction() = !environment.activeProfiles.contains("prod")
    fun notOnline() = environment.activeProfiles.contains("local")
}