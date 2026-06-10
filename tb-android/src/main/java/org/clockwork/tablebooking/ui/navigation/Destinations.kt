package org.clockwork.tablebooking.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Auth {
    @Serializable
    object Login

    @Serializable
    data class Registration (
        val login: String = "",
        val password: String = ""
    )
}

@Serializable
object Home {

    @Serializable
    object ReservationsList

    @Serializable
    object ReservationCreation
}