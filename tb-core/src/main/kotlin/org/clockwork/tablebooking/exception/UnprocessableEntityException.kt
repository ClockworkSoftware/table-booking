package org.clockwork.tablebooking.exception

class UnprocessableEntityException(message: String?) : CommonHttpException(422, message)