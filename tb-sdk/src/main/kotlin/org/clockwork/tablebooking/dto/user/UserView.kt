package org.clockwork.tablebooking.dto.user

data class UserView (
    val id: Long,

    val name: String,
    val surname: String,
    val role: UserRole
)