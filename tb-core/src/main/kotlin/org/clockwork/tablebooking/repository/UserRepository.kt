package org.clockwork.tablebooking.repository

import jakarta.data.repository.Delete
import jakarta.data.repository.Find
import jakarta.data.repository.Repository
import jakarta.data.repository.Save
import org.clockwork.tablebooking.domain.User

@Repository
interface UserRepository {

    @Find
    fun findByLogin(login: String): User?

    @Find
    fun findByLoginAndPassword(login: String, password: String): User?

    @Save
    fun save(user: User): User

    @Delete
    fun deleteById(id: Long)
}