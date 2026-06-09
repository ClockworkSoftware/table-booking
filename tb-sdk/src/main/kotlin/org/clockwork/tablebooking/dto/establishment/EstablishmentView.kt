package org.clockwork.tablebooking.dto.establishment

import org.clockwork.tablebooking.dto.user.UserView

data class EstablishmentView(
    val id: Long,
    val address: String,
    val owner: UserView
)
