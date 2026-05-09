package org.clockwork.tablebooking.dto.reservation

import java.time.Instant

data class ReservationCreationView (
    val start: Instant,
    val finish: Instant,

    val clientId: Long? = null
)