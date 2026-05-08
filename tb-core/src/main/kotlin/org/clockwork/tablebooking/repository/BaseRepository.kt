package org.clockwork.tablebooking.repository

import jakarta.data.repository.Delete
import jakarta.data.repository.Find
import jakarta.data.repository.Save
import java.util.*

interface BaseRepository<T> {
    @Find
    fun findById(id: Long): Optional<T>

    @Save
    fun save(obj: T): T

    @Delete
    fun deleteById(id: Long)
}