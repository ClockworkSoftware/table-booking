package org.clockwork.tablebooking.repository

import jakarta.data.repository.Delete
import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import jakarta.data.repository.Save
import org.clockwork.tablebooking.domain.Place
import java.util.*

@Repository
interface PlaceRepository {

    @Find
    fun findAll(): List<Place>

    @Find
    fun findById(id: Long): Optional<Place>

    @Find
    fun findByLabelNumber(labelNumber: Int): Optional<Place>

    @Save
    fun save(user: Place): Place

    @Delete
    fun deleteById(id: Long)
}