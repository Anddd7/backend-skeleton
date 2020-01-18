package com.github.anddd7

import io.zonky.test.db.postgres.embedded.ConnectionInfo
import io.zonky.test.db.postgres.embedded.FlywayPreparer
import io.zonky.test.db.postgres.embedded.PreparedDbProvider
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.sql.DataSource

@Profile("test")
@SpringBootApplication
internal class TestApplication {
  @Bean
  fun preparedDbProvider() = PreparedDbProvider.forPreparer(
      FlywayPreparer.forClasspathLocation("db/migration")
  )

  @Bean
  fun connectionInfo(preparedDbProvider: PreparedDbProvider): ConnectionInfo {
    return preparedDbProvider.createNewDatabase()
  }

  @Bean
  fun dataSource(preparedDbProvider: PreparedDbProvider, connectionInfo: ConnectionInfo): DataSource {
    return preparedDbProvider.createDataSourceFromConnectionInfo(connectionInfo)
  }

  @Primary
  @Bean
  fun r2dbcProperties(connectionInfo: ConnectionInfo): R2dbcProperties {
    return R2dbcProperties().apply {
      url = "r2dbc:postgresql://127.0.0.1:${connectionInfo.port}/${connectionInfo.dbName}"
      name = "test-r2dbc"
      username = connectionInfo.user
    }
  }
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TestApplication::class]
)
@ActiveProfiles("test")
annotation class EnableApiTest
