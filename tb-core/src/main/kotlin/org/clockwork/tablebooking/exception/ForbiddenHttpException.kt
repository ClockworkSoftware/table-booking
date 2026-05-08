package org.clockwork.tablebooking.exception

class ForbiddenHttpException(message: String? = null) : CommonHttpException(403, message)