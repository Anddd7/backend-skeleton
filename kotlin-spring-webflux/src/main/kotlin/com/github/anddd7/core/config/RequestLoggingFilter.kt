package com.github.anddd7.core.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class RequestLoggingFilter : WebFilter {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain) =
            exchange.also { it.logRequestMessage() }
                    .also { it.logResponseMessage() }
                    .run(chain::filter)

    fun ServerWebExchange.logRequestMessage() {
        listOfNotNull(
                request.method,
                request.uri.path,
                request.headers.accept.takeIf { it.isNotEmpty() }
                        ?.run { HttpHeaders.ACCEPT + ": " + this },
                request.headers.contentType
                        ?.run { HttpHeaders.CONTENT_TYPE + ": " + this }
        )
                .joinToString(" ", prefix = ">>> ")
                .run(log::info)
    }


    fun ServerWebExchange.logResponseMessage() {
        response.beforeCommit {
            listOfNotNull(
                    request.method,
                    request.uri.path,
                    response.statusCode
                            ?.run { "HTTP${value()} $reasonPhrase" },
                    request.headers.contentType
                            ?.run { HttpHeaders.CONTENT_TYPE + ": " + this }
            )
                    .joinToString(" ", prefix = "<<< ")
                    .run(log::info)

            Mono.empty()
        }
    }
}


