package com.github.anddd7

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.function.Consumer

@SpringBootApplication
internal class TestApplication {
  @Bean
  fun embeddedPostgresCustomizer(): Consumer<EmbeddedPostgres.Builder> {
    return Consumer {
      it.setPort(5432)
      it.setCleanDataDirectory(true)
//        it.setConnectConfig("PGDBNAME", "postgres")
//        it.setConnectConfig("user", "postgres")
//        it.setConnectConfig("password", "test")
    }
  }
}

