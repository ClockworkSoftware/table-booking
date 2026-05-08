package org.clockwork.tablebooking.dto.place

data class PlaceView (
    val id: Long,
    val labelNumber: Int,
    val reservationPrice: Double,
    val capacity: Int
) {}