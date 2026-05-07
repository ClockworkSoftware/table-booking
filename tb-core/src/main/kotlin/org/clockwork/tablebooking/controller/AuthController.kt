package org.clockwork.tablebooking.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.clockwork.tablebooking.config.JwtConfig
import org.clockwork.tablebooking.domain.User
import org.clockwork.tablebooking.dto.security.AuthenticatedUserView
import org.clockwork.tablebooking.dto.security.LoginView
import org.clockwork.tablebooking.dto.security.RegistrationView
import org.clockwork.tablebooking.dto.user.UserJwtView
import org.clockwork.tablebooking.exception.UnauthorizedHttpException
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tools.jackson.databind.ObjectMapper
import java.security.MessageDigest
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/v1/public/auth")
class AuthController(
    val userRepo: UserRepository,
    val jwtConfig: JwtConfig
) {
    @Value($$"${app.security.max-token-ttl}")
    var tokenLifespan: Long? = null

    @PostMapping("register")
    fun register(@RequestBody body: RegistrationView): AuthenticatedUserView {
        userRepo.findByLogin(body.login).getOrNull()?.run { throw UnprocessableEntityException("User already exists!") }

        val user = userRepo.save(body.run { User(name, surname, login, password.sha256(), role) })

        return AuthenticatedUserView(generateToken(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginView): AuthenticatedUserView {
        val user = userRepo.findByLoginAndPassword(body.login, body.password.sha256()).getOrNull()
            ?: throw UnauthorizedHttpException("Login or password incorrect!")
        return AuthenticatedUserView(generateToken(user))
    }

    private fun generateToken(user: User): String {
        return JWT.create()
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plusMillis(tokenLifespan!!))
            .withPayload(ObjectMapper().writeValueAsString(
                UserJwtView(
                    user.id!!.toString(),
                    user.name,
                    user.surname,
                    user.role
                ))
            )
            .sign(Algorithm.RSA256(jwtConfig))
    }

    private fun String.sha256(): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}