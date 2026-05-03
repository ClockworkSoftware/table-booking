package org.clockwork.tablebooking.exception

class BadRequestException(message: String?) : CommonHttpException(400, message)