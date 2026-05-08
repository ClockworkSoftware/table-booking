package org.clockwork.tablebooking.dto.place

data class PlaceCreationView (
    val establishmentId: Long,
    val labelNumber: Int,
    val reservationPrice: Double,
    val guestCapacity: Int
)