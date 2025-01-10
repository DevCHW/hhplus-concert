package kr.hhplus.be.server.infra.storage.core

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

inline fun <reified T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: throw EntityNotFoundException("${T::class.simpleName} not found. ID = $id")
}

