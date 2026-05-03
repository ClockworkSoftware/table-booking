package org.clockwork.tablebooking.dto.user

enum class UserRole(
    val authorities: List<String>
) {
    ADMIN(listOf("ROLE_ADMIN", "ROLE_WAITER", "ROLE_CLIENT")),
    WAITER(listOf("ROLE_WAITER", "ROLE_CLIENT")),
    CLIENT(listOf("ROLE_CLIENT"))
}