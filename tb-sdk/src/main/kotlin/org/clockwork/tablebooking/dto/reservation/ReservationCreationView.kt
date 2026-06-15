package org.clockwork.tablebooking.dto.reservation

import java.time.Instant

data class ReservationCreationView (
    val start: Instant,
    val finish: Instant = start.plusSeconds(3600), // + 1 hour

    val clientId: Long? = null
)