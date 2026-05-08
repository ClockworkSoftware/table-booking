package org.clockwork.tablebooking.dto.place

data class CreatePlaceView (
    var labelNumber: Int,
    var reservationPrice: Double,
    var capacity: Int
) {}