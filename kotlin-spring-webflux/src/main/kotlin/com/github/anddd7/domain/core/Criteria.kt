package com.github.anddd7.domain.core

import kotlin.math.ceil

data class Criteria(
    val limit: Int,
    val offset: Long
) {
  fun getRequestPage() = ceil((offset + 1).toDouble() / limit).toInt()

  init {
    require(limit >= 1) { "limit must grater than or equal to 1" }
    require(offset >= 0) { "offset must grater than or equal to 0" }
  }
}
