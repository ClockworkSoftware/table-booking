package org.clockwork.tablebooking.exception

class NotFoundException(message: String?) : CommonHttpException(404, message)