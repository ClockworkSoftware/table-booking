package org.clockwork.tablebooking.dto.user

data class UserJwtView (
    val sub: String,
    val name: String,
    val surname: String,
    val role: UserRole
) {}