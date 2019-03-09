import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

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
}

// configure kotlin's compile options [kotlin-gradle](https://kotlinlang.org/docs/reference/using-gradle.html)
tasks.withType<KotlinCompile> {
    kotlinOptions.apiVersion = "1.3"
    kotlinOptions.languageVersion = "1.3"

    kotlinOptions.jvmTarget = "1.8"
}

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

// apply dependency plugin form spring-boot plugin
apply(plugin = "io.spring.dependency-management")

// project's properties
group = "com.github.anddd7"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

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

    // swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    runtimeOnly("io.springfox:springfox-swagger-ui:2.9.2")

    // different with flyway-plugin, inject this and open spring auto-migration by flyway
    runtimeOnly("org.flywaydb:flyway-core")

    // db
    runtimeOnly("org.postgresql:postgresql")

    // hibernate x postgresql'sjsonb
    implementation("com.vladmihalcea:hibernate-types-52:2.3.2")

    // logstash extension for logback
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:5.2")

    // utils
    implementation("kcom.google.guava:guava:27.0.1-jre")

    // junit5
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

task("newMigration") {
    group = "flyway"
    description = """
        ./gradlew newMigration -Ptype=<ddl,dml> -Poperation=<operation>. Please ensure you already have dir `db/migration`
        """.trim()

    doLast {
        val (operation, type) = properties["operation"] to properties["type"]
        val resourcesPath = sourceSets["main"].resources.sourceDirectories.singleFile.path
        val timestamp = now().format(ofPattern("yyyyMMddHHmm"))
        val filename = "V${timestamp}_${type}_$operation.sql"
        val filepath = "$resourcesPath/db/migration/$filename"
        File(filepath).takeIf { it.createNewFile() }?.appendText("-- script")
    }
}