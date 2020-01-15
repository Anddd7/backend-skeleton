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

basic template

[README](./java-spring/README.md)

## java-spring-ddd

ref:

- https://github.com/howiehu/ddd-architecture-samples
- https://github.com/e-commerce-sample/ecommerce-order-service

## kotlin-spring

[README](./kotlin-spring/README.md)

## kotlin-spring-ddd

[README](./kotlin-spring-ddd/README.md)

## kotlin-spring-webflux

[README](./kotlin-spring-webflux/README.md)

## kotlin-spring-webflux-ddd

[README](./kotlin-spring-webflux/README.md)

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
