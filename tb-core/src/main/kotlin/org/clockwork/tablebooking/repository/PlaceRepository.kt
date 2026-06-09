package org.clockwork.tablebooking.repository

import org.clockwork.tablebooking.domain.Place
import java.util.*

interface PlaceRepository : BaseJpaRepository<Place> {
    fun findByLabelNumber(labelNumber: Int): Optional<Place>

//    @Query("SELECT p FROM Place p LEFT JOIN FETCH p.reservations WHERE p.id = :id")
//    fun findByIdWithReservations(@Param("id") id: Long): Optional<Place>
}