package org.clockwork.tablebooking.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserView (
    val id: Long,

    val name: String,
    val surname: String,
    val role: UserRole
) {
    fun toStringFieldMap(): Map<String, String> {
        return mapOf(
            "sub" to id.toString(),
            "name" to name,
            "surname" to surname,
            "role" to role.name
        )
    }

    companion object {
        fun fromJwt(claims: Map<String, String>): UserView {
            return UserView(
                claims["sub"]!!.toLong(),
                claims["name"]!!,
                claims["surname"]!!,
                UserRole.valueOf(claims["role"]!!)
            )
        }
    }
}