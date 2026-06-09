package org.clockwork.tablebooking.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    val session: AuthSession
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = session.token.value


        if (!originalRequest.url.toString().contains("/api/v1/public/auth")) {
            if (token == null) {
                throw RuntimeException(
                    "Token missing when accessing secured endpoint ${originalRequest.url}!"
                )
            }

            val requestBuilder = originalRequest.newBuilder()
            requestBuilder.addHeader("Authorization", "Bearer $token")
            return chain.proceed(requestBuilder.build())
        }

        return chain.proceed(originalRequest)
    }
}