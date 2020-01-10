package com.github.anddd7.domain.core

import com.github.anddd7.domain.Entity
import java.util.UUID

abstract class DomainException(message: String?) : RuntimeException(message)

class EntityExistedException : DomainException {
  constructor(message: String?) : super(message)
  constructor(entityClass: Class<Entity>, id: UUID) : super(
      "the " + entityClass.simpleName.toLowerCase() + " with id " + id + " was existed"
  )
}

class EntityNotFoundException : DomainException {
  constructor(message: String?) : super(message)
  constructor(entityClass: Class<Entity>, id: UUID) : super(
      "cannot find the " + entityClass.simpleName.toLowerCase() + " with id " + id
  )
}
