package org.clockwork.tablebooking.dto.security

import org.clockwork.tablebooking.dto.user.UserRole

data class RegistrationView(
    val login: String,
    val password: String,
    val name: String,
    val surname: String,
    val role: UserRole
)