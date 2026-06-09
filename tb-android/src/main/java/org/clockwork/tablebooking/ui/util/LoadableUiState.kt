package org.clockwork.tablebooking.ui.util

import androidx.compose.runtime.State

interface LoadableUiState<T> {
    object Idle : LoadableUiState<Unit>
    object Loading : LoadableUiState<Unit>
    data class Success<T>(val result: T) : LoadableUiState<T>
    data class Error(val message: String) : LoadableUiState<Unit>
}

fun State<LoadableUiState<out Any>>.isLoading() : Boolean {
    return this.value == LoadableUiState.Loading
}