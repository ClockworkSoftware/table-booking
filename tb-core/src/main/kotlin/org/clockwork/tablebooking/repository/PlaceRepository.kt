package org.clockwork.tablebooking.repository

import org.clockwork.tablebooking.domain.Place
import java.util.Optional

interface PlaceRepository : BaseJpaRepository<Place> {
    fun findByLabelNumber(labelNumber: Int): Optional<Place>

    fun findByEstablishmentId(establishmentId: Long): List<Place>
}