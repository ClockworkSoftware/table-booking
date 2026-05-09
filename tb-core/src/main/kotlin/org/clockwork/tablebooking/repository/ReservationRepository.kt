package org.clockwork.tablebooking.repository

import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.domain.Reservation

@Repository
interface ReservationRepository : BaseRepository<Reservation> {}
