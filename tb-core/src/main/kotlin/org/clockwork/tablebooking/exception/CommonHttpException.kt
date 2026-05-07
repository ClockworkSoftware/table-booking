package org.clockwork.tablebooking.exception

open class CommonHttpException(
    val code: Int,
    override val message: String? = null
) : RuntimeException(message) {

    class SimplifiedView(
        val code: Int,
        val message: String? = null
    )

    fun simplifiedView(): SimplifiedView {
        return SimplifiedView(code, message)
    }
}