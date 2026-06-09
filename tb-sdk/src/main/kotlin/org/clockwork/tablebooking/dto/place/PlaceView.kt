package org.clockwork.tablebooking.dto.place

import org.clockwork.tablebooking.dto.establishment.EstablishmentView

data class PlaceView (
    val id: Long,
    val labelNumber: Int,
    val reservationPrice: Double,
    val guestCapacity: Int,
    val establishment: EstablishmentView
)