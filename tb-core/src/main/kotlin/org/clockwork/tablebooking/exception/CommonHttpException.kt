package org.clockwork.tablebooking.exception

open class CommonHttpException(
    val code: Int,
    override val message: String? = null
) : RuntimeException(message)