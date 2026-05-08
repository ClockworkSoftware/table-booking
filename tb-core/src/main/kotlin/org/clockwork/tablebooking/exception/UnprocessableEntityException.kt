package org.clockwork.tablebooking.exception

class UnprocessableEntityException(message: String? = null) : CommonHttpException(422, message)