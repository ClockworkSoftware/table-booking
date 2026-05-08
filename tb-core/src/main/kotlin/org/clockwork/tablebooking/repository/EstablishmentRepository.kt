package org.clockwork.tablebooking.repository

import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.Establishment

@Repository
interface EstablishmentRepository : BaseRepository<Establishment> {}
