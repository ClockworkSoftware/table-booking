package org.clockwork.tablebooking.repository

import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.Establishment
import java.util.Optional

@Repository
interface EstablishmentRepository : BaseRepository<Establishment> {

    @Find
    fun findByAddress(address: String): Optional<Establishment>

}
