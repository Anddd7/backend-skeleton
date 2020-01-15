package com.github.anddd7.core.service

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CacheableService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(cacheNames = ["simply-cache"])
    fun getValue(key: String): Long {
        logger.debug("Get value in service by : $key")
        return Random.nextLong()
    }
}
