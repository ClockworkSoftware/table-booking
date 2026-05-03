package org.clockwork.tablebooking.controller.filter

import com.auth0.jwt.JWT
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.kotlin.Logging
import org.clockwork.tablebooking.domain.User
import org.clockwork.tablebooking.exception.UnauthorizedHttpException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.concurrent.TimeUnit

@Component
class AuthFilter (
    @Value($$"${app.security.inactive-session-timeout}")
    var inactiveSessionTtl: Long
) : OncePerRequestFilter(), Logging {
    val tokenCache: Cache<String, User> = CacheBuilder<String, User>.newBuilder()
        .expireAfterAccess(inactiveSessionTtl, TimeUnit.MILLISECONDS).build()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val httpRequest = (request as HttpServletRequest)

        val authHeader: String? = httpRequest.getHeader("Authentication")
        if (authHeader == null || authHeader.isEmpty()) {
            if (!request.requestURI.startsWith("/api/v1/public/auth/")) {
                throw UnauthorizedHttpException()
            }
            filterChain.doFilter(request, response)
            return
        }

        val jwt = JWT.decode(authHeader)
        logger.debug { jwt.claims }

        filterChain.doFilter(request, response)
    }
}