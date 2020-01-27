# backend-skeleton

Java, Kotlin, Spring MVC, Spring JPA, Spring Security, Spring Cloud...

## Configure IDEA

1. Import code style

Open IDEA, enter `Preferences -> Editor -> Code Style -> Import Schema`
Choose `GoogleStyle.xml`

This style is based on GoogleStyle and default Kotlin style of Intellij Team, is suitable for both Java and Kotlin.

2. New line end of file

Enter `Preferences -> Editor -> General`
Check on `Other: Ensure line feed at file end on Save`

# Projects

## java-spring

basic template, deprecate

- just search in github, you can find many kinds of samples

[README](./java-spring/README.md)

## java-spring-ddd

Good template from Thoughtworkers:

- https://github.com/howiehu/ddd-architecture-samples
- https://github.com/e-commerce-sample/ecommerce-order-service

## kotlin-spring

done

[README](https://github.com/Anddd7/kotlin-spring-template/README.md)

## kotlin-spring-ddd

extend **kotlin-spring**, in progress

[README](https://github.com/Anddd7/kotlin-spring-ddd-template/README.md)

## kotlin-spring-webflux

in progress

- security
- transactional

[README](https://github.com/Anddd7/kotlin-spring-webflux-template/README.md)

## kotlin-spring-webflux-ddd

extend **kotlin-spring-webflux** and **kotlin-spring-ddd**, done

[README](https://github.com/Anddd7/kotlin-spring-webflux-ddd-template/README.md)

# Technical Reference

## 架构

- DDD
- Clean Architecture

## 核心

- Kotlin
- Spring Framework
  - Spring Core: AOP/DI
  - Spring MVC
  - Spring WebFlux
  - Spring JPA
  - Spring R2DBC
  - Spring Security
  - Spring Boot
  - Spring Test
- Reactive Streams
  - project-reactor
  - RxJava

## 工具/框架

- mockK
- deteKt
- junit5
- embedded-database-spring-test
- archunit
- docker
  - docker-compose
- gradle
- logstash
- assertj
