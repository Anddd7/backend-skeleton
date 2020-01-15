package com.github.anddd7.core.service

import com.github.anddd7.core.model.Temp
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("local", "test")
class TempService {
    private val temp: Temp by lazy { Temp() }

    fun getVersion() = temp.version
}
