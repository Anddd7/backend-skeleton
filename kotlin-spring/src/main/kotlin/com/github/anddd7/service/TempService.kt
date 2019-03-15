package com.github.anddd7.service

import com.github.anddd7.model.Temp
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("local")
class TempService {
    private val temp: Temp by lazy { Temp() }

    fun getVersion() = temp.version
}
