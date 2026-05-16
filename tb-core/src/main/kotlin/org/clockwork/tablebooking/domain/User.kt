package org.clockwork.tablebooking.domain

import jakarta.persistence.*
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.dto.user.UserView

@Entity
@Table(name = "T_USER")
data class User (
    val name: String,
    val surname: String,

    @Column(unique = true)
    val login: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole,
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    fun toDto(): UserView {
        return UserView(
            id!!,
            name,
            surname,
            role
        )
    }
}