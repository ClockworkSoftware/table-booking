package org.clockwork.tablebooking.exception

class BadRequestException(message: String? = null) : CommonHttpException(400, message)