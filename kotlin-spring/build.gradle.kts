import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

/** -------------- project's properties -------------- */

group = "com.github.anddd7"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

/** -------------- import & apply plugins -------------- */

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        // import dependencies for flyway plugin
        classpath("org.postgresql:postgresql:42.2.5")
    }
}

// import plugins into this project
plugins {
    val kotlinVersion = "1.3.21"

    // core plugins, which is already include in plugin dependencies spec
    java
    idea
    kotlin("jvm") version kotlinVersion

    /**
     * binary(external) plugins, provide id and version to resolve it
     * base plugin for spring-boot, provide plugins and tasks
     */
    id("org.springframework.boot") version "2.1.1.RELEASE"

    // [spring support](https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support)
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion

    id("org.flywaydb.flyway") version "5.2.4"

    // base on `kotlin-noarg`, generate default method for entity
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion

    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC14"
}

// apply dependency plugin form spring-boot plugin
apply(plugin = "io.spring.dependency-management")

/** -------------- configure imported plugin -------------- */

idea {
    module {
        outputDir = file("$buildDir/classes/main")
        testOutputDir = file("$buildDir/classes/test")
        jdkName = "1.8"
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/test?user=test&password=test"
}

detekt {
    toolVersion = "1.0.0-RC14"
    input = files("src/main/kotlin")
    filters = ".*/resources/.*,.*/build/.*"
}

/** -------------- configure core task -------------- */

// configure kotlin's compile options [kotlin-gradle](https://kotlinlang.org/docs/reference/using-gradle.html)
tasks.withType<KotlinCompile> {
    kotlinOptions.apiVersion = "1.3"
    kotlinOptions.languageVersion = "1.3"
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    testLogging {
        quiet {
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
}

/** -------------- dependencies management -------------- */

dependencies {
    /**
     * `compile` is deprecated;
     *
     * `implementation` can only access by this project
     * `api` can access by top-level project which include this project
     */
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // validation extension
    implementation("org.hibernate.validator:hibernate-validator:6.0.15.Final")

    // jackson extension
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")

    // swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    runtimeOnly("io.springfox:springfox-swagger-ui:2.9.2")

    // different with flyway-plugin, inject this and open spring auto-migration by flyway
    runtimeOnly("org.flywaydb:flyway-core")

    // postgres
    runtimeOnly("org.postgresql:postgresql")
    // test with postgres
    testImplementation("io.zonky.test:embedded-database-spring-test:1.4.1")
    testRuntimeOnly("org.testcontainers:postgresql:1.8.3")

    // hibernate x postgresql'sjsonb
    implementation("com.vladmihalcea:hibernate-types-52:2.3.2")

    // logstash extension for logback
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:5.2")

    // utils
    implementation("com.google.guava:guava:27.0.1-jre")

    // junit5
    testImplementation("org.junit.jupiter:junit-jupiter-engine")

    // testing
    testImplementation("io.mockk:mockk:1.8.13")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

/** -------------- new task -------------- */

task("newMigration") {
    group = "flyway"
    description = """
        ./gradlew newMigration -Ptype=<ddl,dml> -Poperation=<operation>. Please ensure you already have dir `db/migration`
        """.trim()

    doLast {
        val (operation, type) = properties["operation"] to properties["type"]
        val resourcesPath = sourceSets["main"].resources.sourceDirectories.singleFile.path
        val timestamp = now().format(ofPattern("yyyyMMddHHmm"))
        val filename = "V${timestamp}__${type}_$operation.sql"
        val filepath = "$resourcesPath/db/migration/$filename"
        File(filepath).takeIf { it.createNewFile() }?.appendText("-- script")
    }
}
