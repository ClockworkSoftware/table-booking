package org.clockwork.tablebooking.exception

class UnauthorizedHttpException(message: String? = null) : CommonHttpException(401, message)