package org.clockwork.tablebooking.ui.navigation

import kotlinx.serialization.Serializable
import org.clockwork.tablebooking.dto.user.UserView

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

//@Serializable
//data class Home(
//    val user: UserView
//)