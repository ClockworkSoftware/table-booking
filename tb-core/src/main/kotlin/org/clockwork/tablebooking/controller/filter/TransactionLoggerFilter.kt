package org.clockwork.tablebooking.controller.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(1)
class TransactionLoggerFilter : OncePerRequestFilter(), Logging {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.debug { "-> ${request.method} ${request.requestURI}" }
        logger.debug { "${response.status} <- ${request.method} ${request.requestURI}" }

        filterChain.doFilter(request, response)
    }
}