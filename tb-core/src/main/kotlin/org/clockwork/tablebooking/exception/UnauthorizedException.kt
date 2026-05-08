package org.clockwork.tablebooking.exception

class UnauthorizedException(message: String? = null) : CommonHttpException(401, message)