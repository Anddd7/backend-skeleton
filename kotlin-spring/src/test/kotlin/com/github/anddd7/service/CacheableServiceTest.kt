package com.github.anddd7.service

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureEmbeddedDatabase
internal class CacheableServiceTest {

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Autowired
    private lateinit var cacheableService: CacheableService

    @Test
    fun `should have existing cache `() {
        assertThat(cacheManager.getCache("simply-cache")).isNotNull
    }

    @Test
    fun `should return cached value from existing cache `() {
        val cache = cacheManager.getCache("simply-cache")!!
        assertThat(cache.get("key")).isNull()

        val realValue = cacheableService.getValue("key")
        val cachedValue = cacheableService.getValue("key")

        assertThat(realValue).isEqualTo(cachedValue)
        assertThat(realValue).isEqualTo(cache.get("key")?.get())

        assertThat(realValue).isNotEqualTo(cacheableService.getValue("next"))
    }
}
