package org.clockwork.tablebooking.exception

open class NotFoundException(message: String? = null) : CommonHttpException(404, message)