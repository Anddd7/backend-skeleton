package com.github.anddd7.core.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object JacksonConfig {
  val Global: ObjectMapper = configure(ObjectMapper())

  fun configure(objectMapper: ObjectMapper): ObjectMapper =
      objectMapper
          .registerKotlinModule()
          .registerModule(JavaTimeModule())
          .setSerializationInclusion(JsonInclude.Include.NON_NULL)
          .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
          .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
          .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
}
