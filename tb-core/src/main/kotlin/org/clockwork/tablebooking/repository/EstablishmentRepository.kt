package org.clockwork.tablebooking.repository

import org.clockwork.tablebooking.domain.Establishment
import java.util.*

interface EstablishmentRepository : BaseJpaRepository<Establishment> {
    fun findByAddress(address: String): Optional<Establishment>
}
