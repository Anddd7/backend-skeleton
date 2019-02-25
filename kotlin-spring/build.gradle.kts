/**
 * import plugins into this project
 */
plugins {
    // core plugins, which is already include in plugin dependencies spec
    java
    idea
    kotlin("jvm") version "1.3.21"

    // binary(external) plugins, provide id and version to resolve it
    // base plugin for spring-boot, provide plugins and tasks
    id("org.springframework.boot") version "2.1.1.RELEASE"
}

// apply dependency plugin form spring-boot plugin
apply(plugin = "io.spring.dependency-management")

idea {
    module {
        outputDir = file("$buildDir/classes/main")
        testOutputDir = file("$buildDir/classes/test")
        jdkName = "1.8"
    }
}

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

    implementation("org.springframework.boot:spring-boot-starter-web")
    
    // depended by spring, won't compile
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
