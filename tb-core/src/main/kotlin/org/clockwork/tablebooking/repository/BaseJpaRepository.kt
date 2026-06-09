package org.clockwork.tablebooking.repository

import org.clockwork.tablebooking.exception.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseJpaRepository<T : Any> : JpaRepository<T, Long>

inline fun <reified T : Any> BaseJpaRepository<T>.findByIdOrThrow(id: Long): T {
    return findById(id).orElseThrow { EntityNotFoundException(T::class.simpleName!!, id) }
}