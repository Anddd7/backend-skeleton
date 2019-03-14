package com.github.anddd7.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
class RequestLoggingFilterConfig {
    @Bean
    fun logFilter() =
        CommonsRequestLoggingFilter().apply {
            setIncludeHeaders(false)
            setIncludeQueryString(true)
            setIncludePayload(true)
            setMaxPayloadLength(10000)
        }
}