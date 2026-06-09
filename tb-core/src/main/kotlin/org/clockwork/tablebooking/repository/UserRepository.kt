package org.clockwork.tablebooking.repository

import org.clockwork.tablebooking.domain.User
import java.util.*

interface UserRepository : BaseJpaRepository<User> {
    fun findByLogin(login: String): Optional<User>
    fun findByLoginAndPassword(login: String, password: String): Optional<User>
}