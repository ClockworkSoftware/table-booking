package org.clockwork.tablebooking.exception

class InternalServerError(message: String?) : CommonHttpException(500, message)