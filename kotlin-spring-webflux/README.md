# Kotlin & Spring WebFlux

参考[webflux学习路径](https://xwjie.github.io/webflux/webflux-study-path.html), 方便大家学习Kotlin和SpringWebFlux的开发

## Why Kotlin & Spring WebFlux

Kotlin
- JVM语言
- 语法糖/函数式编程
- intellij亲儿子
- 协程

> Kotlin相对于Java来说, 主要是提供了大量的语法糖, 减少了代码量. 
  空值检测也是有效的环节了Java NullPointerException的恶心程度.
  Kotlin Coroutines则是从语法层面, 降低了JVM线程的使用难度.
  
Spring WebFlux
- Spring5核心功能
- 实现了Reactive Streams
- 高性能异步I/O
- Spring小儿子

> WebFlux则是基于Reactive模式, 提供了高性能的响应机制. 结合Spring生态圈和Netty/RxJava等优秀框架. 

## Prerequisite

最好有:

- Java 基础
- Kotlin 基础
- Spring Boot 基础

## Kotlin Coroutines

看就完事: [码上开学 - Kotlin指南](https://kaixue.io/)
Sample Code -> https://github.com/Anddd7/algorithm-lab/tree/master/leetcode-kotlin/src/test/kotlin/com/github/anddd7/coroutines

## Lambda & Stream

Java8 支持了Lambda, 并且提供了一套Stream API, 简化对集合类的操作
- Lambda降低了函数式方法的实现难度
- Stream简化代码的同时, 还提供了并行的处理方式
    - 不存储数据
    - 函数式
    - 延迟操作
    - 短路操作
    - 并行

Sample Code -> https://github.com/Anddd7/algorithm-lab/tree/master/algorithm-java/src/main/java/com/github/anddd7/jdk8/stream

## Reactive Stream & Spring Reactor

## Spring WebFlux

## Kotlin with Spring WebFlux
