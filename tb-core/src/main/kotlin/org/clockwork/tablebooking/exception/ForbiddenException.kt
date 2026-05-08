package org.clockwork.tablebooking.exception

class ForbiddenException(message: String? = null) : CommonHttpException(403, message)