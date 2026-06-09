package org.clockwork.tablebooking.repository

import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.domain.Reservation

interface ReservationRepository : BaseJpaRepository<Reservation> {
    fun findByPlaceEstablishment(establishment: Establishment): List<Reservation>
    fun findByClientId(clientId: Long): List<Reservation>
}
