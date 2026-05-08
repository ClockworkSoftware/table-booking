package org.clockwork.tablebooking.repository

import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import org.clockwork.tablebooking.domain.User
import java.util.*

@Repository
interface UserRepository : BaseRepository<User> {

    @Find
    fun findByLogin(login: String): Optional<User>

    @Find
    fun findByLoginAndPassword(login: String, password: String): Optional<User>
}