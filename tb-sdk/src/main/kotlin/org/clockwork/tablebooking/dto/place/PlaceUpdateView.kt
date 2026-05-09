package org.clockwork.tablebooking.dto.place

data class PlaceUpdateView (
    val id: Long,
    val labelNumber: Int? = null,
    val reservationPrice: Double? = null,
    val guestCapacity: Int? = null
)