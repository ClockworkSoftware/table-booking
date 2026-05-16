package org.clockwork.tablebooking.repository

import jakarta.data.repository.By
import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.domain.Reservation

@Repository
interface ReservationRepository : BaseRepository<Reservation> {

    @Find
    fun findByEstablishment(@By("place.establishment") establishment: Establishment): List<Reservation>

    @Find
    fun findByClientId(@By("client.id") clientId: Long): List<Reservation>
}
