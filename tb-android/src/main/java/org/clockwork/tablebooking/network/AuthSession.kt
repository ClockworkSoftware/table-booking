package org.clockwork.tablebooking.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object AuthSession {
    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun saveToken(token: String) {
        _token.update { token }
    }

    fun clearToken() {
        _token.update { null }
    }
}