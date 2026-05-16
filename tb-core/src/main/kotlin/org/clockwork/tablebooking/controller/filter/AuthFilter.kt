package org.clockwork.tablebooking.controller.filter

import com.auth0.jwt.JWT
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.kotlin.Logging
import org.clockwork.tablebooking.domain.User
import org.clockwork.tablebooking.dto.user.UserJwtView
import org.clockwork.tablebooking.exception.UnauthorizedException
import org.clockwork.tablebooking.util.UserContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.concurrent.TimeUnit

@Component
class AuthFilter (
    @Value($$"${app.security.inactive-session-timeout}")
    val inactiveSessionTtl: Long,
    val userContext: UserContext
) : OncePerRequestFilter(), Logging {
    val tokenCache: Cache<String, User> = CacheBuilder<String, User>.newBuilder()
        .expireAfterAccess(inactiveSessionTtl, TimeUnit.MILLISECONDS).build()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
            if (!request.requestURI.startsWith("/api/v1/public/auth/")) {
                throw UnauthorizedException()
            }
            filterChain.doFilter(request, response)
            return
        }

        val jwt = JWT.decode(authHeader.substring("Bearer ".length))
        logger.debug { jwt.claims.mapValues { it.value.toString() }.toString() }

        userContext.jwtView = UserJwtView.fromJwt(jwt.claims.mapValues { it.value.asString() })
        logger.debug("Authenticated user ${userContext.jwtView.name}")

        filterChain.doFilter(request, response)
    }
}