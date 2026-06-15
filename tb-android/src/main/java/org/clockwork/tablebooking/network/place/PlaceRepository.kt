package org.clockwork.tablebooking.network.auth

import android.util.Log
import com.auth0.jwt.JWT
import org.clockwork.tablebooking.dto.security.LoginView
import org.clockwork.tablebooking.dto.security.RegistrationView
import org.clockwork.tablebooking.dto.user.UserView
import org.clockwork.tablebooking.network.AuthSession
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    val apiService: AuthApiService,
    val authSession: AuthSession
) {
    suspend fun registerUser(
        name: String,
        surname: String,
        login: String,
        password: String
    ): Result<UserView> {
        return try {
            val response = apiService.register(RegistrationView(
                login,
                password,
                name,
                surname
            ))
            authSession.saveToken(response.token)
            val userView = UserView.fromJwt(
                JWT.decode(response.token).claims.mapValues { it.value.asString() }
            )
            Result.success(userView)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(login: String, password: String): Result<UserView> {
        return try {
            val response = apiService.login(LoginView(login, password))
            val userView = UserView.fromJwt(
                JWT.decode(response.token).claims.mapValues { it.value.asString() }
            )
            authSession.saveToken(response.token)
            Result.success(userView)
        } catch (e: Exception) {
            Log.e("RepoLogin", e.message ?: "err")
            Result.failure(e)
        }
    }
}