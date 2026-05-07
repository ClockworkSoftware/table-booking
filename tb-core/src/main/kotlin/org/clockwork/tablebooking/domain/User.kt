package org.clockwork.tablebooking.domain

import jakarta.persistence.*
import org.clockwork.tablebooking.dto.user.UserRole
import org.springframework.transaction.annotation.Transactional

@Entity
@Table(name = "T_USER")
data class User (
    val name: String,
    val surname: String,

    @Column(unique = true)
    var login: String,
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole,
) {
    @Id
    @GeneratedValue
    val id: Long? = null
}