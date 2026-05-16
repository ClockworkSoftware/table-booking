package org.clockwork.tablebooking.data.auth

import android.util.Log
import com.auth0.jwt.JWT
import org.clockwork.tablebooking.dto.security.LoginView
import org.clockwork.tablebooking.dto.user.UserJwtView

class AuthRepository(val apiService: AuthApiService) {

    suspend fun registerUser(login: String, password: String): Result<UserJwtView> {
        return try {
            val response = apiService.login(LoginView(login, password))
            val userView = UserJwtView.fromJwt(
                JWT.decode(response.token).claims.mapValues { it.value.asString() }
            )
            Result.success(userView)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(login: String, password: String): Result<UserJwtView> {
        return try {
            val response = apiService.login(LoginView(login, password))
            val userView = UserJwtView.fromJwt(
                JWT.decode(response.token).claims.mapValues { it.value.asString() }
            )
            Result.success(userView)
        } catch (e: Exception) {
            Log.e("RepoLogin", e.message ?: "err")
            Result.failure(e)
        }
    }
}