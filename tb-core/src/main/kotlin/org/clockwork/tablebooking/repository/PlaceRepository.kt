package org.clockwork.tablebooking.repository

import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.Place
import java.util.*

@Repository
interface PlaceRepository : BaseRepository<Place> {

    @Find
    fun findAll(): List<Place>

//    @Find
//    fun findById(id: Long): Optional<Place>

    @Find
    fun findByLabelNumber(labelNumber: Int): Optional<Place>
//
//    @Save
//    fun save(place: Place): Place
//
//    @Delete
//    fun deleteById(id: Long)
}