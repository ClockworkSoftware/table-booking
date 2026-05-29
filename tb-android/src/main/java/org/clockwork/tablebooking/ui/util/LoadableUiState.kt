package org.clockwork.tablebooking.ui.util

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow
import org.clockwork.tablebooking.dto.user.UserJwtView

interface LoadableUiState {
    object Idle : LoadableUiState
    object Loading : LoadableUiState
    data class Success(val user: UserJwtView) : LoadableUiState
    data class Error(val message: String) : LoadableUiState
}

fun State<LoadableUiState>.isLoading() : Boolean {
    return this.value == LoadableUiState.Loading
}