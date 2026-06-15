package org.clockwork.tablebooking.network.auth

import org.clockwork.tablebooking.dto.security.AuthenticatedUserView
import org.clockwork.tablebooking.dto.security.LoginView
import org.clockwork.tablebooking.dto.security.RegistrationView
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v1/public/auth/register")
    suspend fun register(@Body request: RegistrationView): AuthenticatedUserView

    @POST("api/v1/public/auth/login")
    suspend fun login(@Body request: LoginView): AuthenticatedUserView
}