package org.clockwork.tablebooking.util

import org.clockwork.tablebooking.dto.user.UserJwtView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.ForbiddenHttpException
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserContext {
    lateinit var jwtView: UserJwtView

    fun hasRole(vararg allowedRoles: UserRole): Boolean {
        return allowedRoles.contains(jwtView.role)
    }

    fun requireRole(vararg requiredRoles: UserRole) {
        if (!hasRole(*requiredRoles)) throw ForbiddenHttpException("No access")
    }
}