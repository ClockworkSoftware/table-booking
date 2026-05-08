package org.clockwork.tablebooking.exception

class InternalServerError(message: String? = null) : CommonHttpException(500, message)