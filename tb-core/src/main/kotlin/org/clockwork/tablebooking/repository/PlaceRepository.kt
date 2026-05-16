package org.clockwork.tablebooking.repository

import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.Place
import java.util.*

@Repository
interface PlaceRepository : BaseRepository<Place> {

    @Find
    fun findByLabelNumber(labelNumber: Int): Optional<Place>
}