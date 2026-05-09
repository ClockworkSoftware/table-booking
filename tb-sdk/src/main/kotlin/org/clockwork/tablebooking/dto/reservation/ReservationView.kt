package org.clockwork.tablebooking.dto.reservation

import org.clockwork.tablebooking.dto.user.UserView
import java.time.Instant

data class ReservationView (
    val start: Instant,
    val finish: Instant,
    val begun: Boolean,
    val client: UserView
)