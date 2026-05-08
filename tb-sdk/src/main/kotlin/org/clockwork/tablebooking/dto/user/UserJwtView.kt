package org.clockwork.tablebooking.dto.user

data class UserJwtView (
    val sub: String,
    val name: String,
    val surname: String,
    val role: UserRole
) {
    companion object {
        fun fromJwt(claims: Map<String, String>): UserJwtView {
            return UserJwtView(
                claims["sub"]!!,
                claims["name"]!!,
                claims["surname"]!!,
                UserRole.valueOf(claims["role"]!!)
            )
        }
    }
}