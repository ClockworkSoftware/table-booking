package org.clockwork.tablebooking.ui.features.auth

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.clockwork.tablebooking.data.auth.AuthApiService
import org.clockwork.tablebooking.data.auth.AuthRepository
import org.clockwork.tablebooking.dto.user.UserJwtView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Success(val user: UserJwtView) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthViewModel(
    val repository: AuthRepository
) : ViewModel() {

    var uiState: AuthUiState by mutableStateOf(AuthUiState.Idle)
        private set

    val loginState = TextFieldState()
    val passwordState = TextFieldState()

    fun performLogin() {
        viewModelScope.launch {
            uiState = AuthUiState.Loading
            repository.loginUser(loginState.text as String, passwordState.text as String)
                .onSuccess { uiState = AuthUiState.Success(it) }
                .onFailure { error ->
                    uiState = AuthUiState.Error(error.localizedMessage ?: "Unknown error")
                }
        }
    }

    // MANUAL FACTORY: Creates the ViewModel without DI libraries
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Initialize Retrofit manually
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://tablebooking.dragonmadness-home.crazedns.ru")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(AuthApiService::class.java)
                val repository = AuthRepository(apiService)
                return AuthViewModel(repository) as T
            }
        }
    }
}