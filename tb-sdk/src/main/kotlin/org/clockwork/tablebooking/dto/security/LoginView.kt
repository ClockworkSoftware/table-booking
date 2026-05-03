package org.clockwork.tablebooking.dto.security

import org.clockwork.tablebooking.dto.user.UserRole

data class LoginView(
    val login: String,
    val password: String
)