package org.clockwork.tablebooking.ui.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.clockwork.tablebooking.network.auth.AuthRepository
import org.clockwork.tablebooking.ui.components.RoundedSquareButton
import org.clockwork.tablebooking.ui.components.SecureInputField
import org.clockwork.tablebooking.ui.components.SubtitleText
import org.clockwork.tablebooking.ui.theme.AppTheme
import org.clockwork.tablebooking.ui.util.LoadableUiState
import org.clockwork.tablebooking.ui.util.isLoading
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repo: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoadableUiState<out Any>>(LoadableUiState.Idle)
    val uiState = _uiState.asStateFlow()

    val loginState = TextFieldState()
    val passwordState = TextFieldState()

    fun performLogin() {
        viewModelScope.launch {
            _uiState.update { LoadableUiState.Loading }
            repo.loginUser(loginState.text as String, passwordState.text as String)
                .onSuccess { user ->
                    _uiState.update { LoadableUiState.Success(user) }
                }
                .onFailure { error ->
                    _uiState.update {
                        LoadableUiState.Error(error.localizedMessage ?: "Unknown error")
                    }
                }
        }
    }
}

@Composable
fun LoginContent(
    uiState: StateFlow<LoadableUiState<out Any>>,
    loginState: TextFieldState,
    passwordState: TextFieldState,
    onLoginButton: () -> Unit,
    onRegisterButton: () -> Unit
    ) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        SubtitleText("Авторизация", Modifier.align(BiasAlignment.Horizontal(0f)))

        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text("Логин") },
            state = loginState,
            enabled = !uiState.collectAsState().isLoading()
        )

        Spacer(Modifier.height(10.dp))
        SecureInputField("Пароль", passwordState, !uiState.collectAsState().isLoading())

        Spacer(Modifier.weight(1f))

        RoundedSquareButton(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            enabled = !loginState.text.isEmpty() && !passwordState.text.isEmpty()
                    && !uiState.collectAsState().isLoading(),
            onClick = onLoginButton
        ) {
            Text(if (uiState.collectAsState().isLoading()) "Выполняется вход..." else "Войти")
            if (uiState.collectAsState().isLoading()) {
                CircularProgressIndicator()
            }
        }

        Spacer(Modifier.height(10.dp))
        RoundedSquareButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRegisterButton
        ) {
            Text("Регистрация")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    AppTheme {
        LoginContent(
            MutableStateFlow(LoadableUiState.Idle).asStateFlow(),
            TextFieldState(),
            TextFieldState(),
            {},
            {}
        )
    }
}