package org.clockwork.tablebooking.util

import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.dto.user.UserView
import org.clockwork.tablebooking.exception.ForbiddenException
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserContext {
    lateinit var currentUser: UserView

    fun hasRole(vararg allowedRoles: UserRole): Boolean {
        return allowedRoles.any { it >= currentUser.role }
    }

    fun requireRole(vararg requiredRoles: UserRole) {
        if (!hasRole(*requiredRoles)) throw ForbiddenException("No access")
    }
}