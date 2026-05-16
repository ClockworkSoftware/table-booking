package org.clockwork.tablebooking.dto.reservation

data class ReservationSearchBody(
    val showPastReservations: Boolean = false,
    val establishmentId: Long? = null
)
