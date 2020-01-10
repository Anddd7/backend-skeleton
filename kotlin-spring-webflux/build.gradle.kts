import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
  val kotlinVersion = "1.3.61"

  idea
  java
  jacoco

  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion

  id("org.flywaydb.flyway") version "6.1.3"

  id("org.springframework.boot") version "2.2.2.RELEASE"
  id("io.spring.dependency-management") version "1.0.8.RELEASE"
  id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
  id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion

  id("io.gitlab.arturbosch.detekt") version "1.3.0"
}

group = "com.anddd7"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
  jcenter()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  // spring
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-security")

  // spring test
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "junit")
    exclude(group = "org.mockito")
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  testImplementation("com.ninja-squad:springmockk:1.1.3")
  testImplementation("org.springframework.security:spring-security-test")

  // junit5
  testImplementation("org.junit.jupiter:junit-jupiter-engine")

  // flyway
  runtimeOnly("org.flywaydb:flyway-core")

  // postgres
  runtimeOnly("org.postgresql:postgresql")
  implementation("com.vladmihalcea:hibernate-types-52:2.3.2")

  // test with postgres
  testImplementation("io.zonky.test:embedded-database-spring-test:1.5.2")
  testRuntimeOnly("org.testcontainers:postgresql:1.12.4")

  // logstash extension for logback
  runtimeOnly("net.logstash.logback:logstash-logback-encoder:5.2")

  // unit test
  testImplementation("io.mockk:mockk:1.9.3")
  testImplementation("org.assertj:assertj-core:3.14.0")
  testImplementation("io.projectreactor:reactor-test")

  // jwt
  implementation("io.jsonwebtoken:jjwt:0.9.1")

  // archiunit
  testImplementation("com.tngtech.archunit:archunit-junit5-api:0.12.0")
  testRuntimeOnly("com.tngtech.archunit:archunit-junit5-engine:0.12.0")
}

idea {
  module {
    outputDir = file("$buildDir/classes/main")
    testOutputDir = file("$buildDir/classes/test")
    jdkName = "11"
  }
}

flyway {
  url = "jdbc:postgresql://localhost:5432/local?user=test&password=test"
}

detekt {
  toolVersion = "1.1.1"
  input = files("src/main/kotlin")
}

jacoco {
  toolVersion = "0.8.3"
}

configure<NoArgExtension> {
  annotation("javax.persistence.Entity")
}

val sourceSets = the<SourceSetContainer>()

sourceSets {
  create("apiTest") {
    java.srcDir("src/apiTest/kotlin")
    resources.srcDir("src/apiTest/resources")
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath
  }
}

tasks.register<Test>("apiTest") {
  description = "Runs the api tests."
  group = "verification"
  testClassesDirs = sourceSets["apiTest"].output.classesDirs
  classpath = sourceSets["apiTest"].runtimeClasspath
  mustRunAfter(tasks["test"])
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile>().all {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
  // include("**/special/package/**") // only analyze a sub package inside src/main/kotlin
  // exclude("**/special/package/internal/**") // but exclude our legacy internal package
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
    val filename = "V${timestamp}__${type}_$operation.sql"
    val filepath = "$resourcesPath/db/migration/$filename"
    File(filepath).takeIf { it.createNewFile() }?.appendText("-- script")
  }
}
