package com.github.anddd7.adapters

import com.github.anddd7.domain.HumbleObject

interface RequestDTO
interface ResponseDTO

interface Handler
interface ReactiveHandler

interface Client

interface PersistenceObject<T> : HumbleObject {
  fun toDomainModel(): T
}
