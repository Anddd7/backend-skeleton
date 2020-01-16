package com.github.anddd7.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class PostgresConfig(private val properties: PostgresProperties) : AbstractR2dbcConfiguration() {
  @Bean
  override fun connectionFactory(): ConnectionFactory =
      PostgresqlConnectionFactory(
          PostgresqlConnectionConfiguration.builder()
              .host(properties.host)
              .port(properties.port)
              .username(properties.username)
              .password(properties.password)
              .database(properties.database)
              .build()
      )
}

@Configuration
@ConfigurationProperties("r2dbc")
class PostgresProperties {
  var host: String = "localhost"
  var port: Int = 5432
  lateinit var username: String
  var password: String = ""
  lateinit var database: String
}
