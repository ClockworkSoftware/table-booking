package org.clockwork.tablebooking.dto.user

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    ADMIN,
    OWNER,
    WAITER,
    CLIENT
}