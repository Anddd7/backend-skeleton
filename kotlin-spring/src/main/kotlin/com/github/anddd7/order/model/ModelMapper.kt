package com.github.anddd7.order.model

import java.util.Optional

interface ModelMapper<T, M, A> {

    fun toDTO(model: M): T

    fun toDAO(model: M): A

    fun fromDTO(dto: T): M

    fun fromDAO(dao: A): M

    fun toDTO(models: Collection<M>) = models.map { toDTO(it) }

    fun toDAO(models: Collection<M>) = models.map { toDAO(it) }

    fun fromDTO(dtos: Collection<T>) = dtos.map { fromDTO(it) }

    fun fromDAO(daos: Collection<A>) = daos.map { fromDAO(it) }
}
