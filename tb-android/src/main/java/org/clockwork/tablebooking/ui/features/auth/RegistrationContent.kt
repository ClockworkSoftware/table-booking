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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.clockwork.tablebooking.network.auth.AuthRepository
import org.clockwork.tablebooking.ui.components.InputField
import org.clockwork.tablebooking.ui.components.SquareButton
import org.clockwork.tablebooking.ui.components.SubtitleText
import org.clockwork.tablebooking.ui.theme.AppTheme
import org.clockwork.tablebooking.ui.util.LoadableUiState
import org.clockwork.tablebooking.ui.util.isLoading
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val repo: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoadableUiState<out Any>> =
        MutableStateFlow(LoadableUiState.Idle)
    val uiState = _uiState.asStateFlow()

    val nameState = TextFieldState()
    val surnameState = TextFieldState()
    val loginState = TextFieldState(savedStateHandle["login"] ?: "")
    val passwordState = TextFieldState(savedStateHandle["password"] ?: "")

    fun performRegistration() {
        viewModelScope.launch {
            _uiState.update { LoadableUiState.Loading }
            repo.registerUser(
                nameState.text as String,
                surnameState.text as String,
                loginState.text as String,
                passwordState.text as String
            )
                .onSuccess { user -> _uiState.update { LoadableUiState.Success(user) } }
                .onFailure { error ->
                    _uiState.update {
                        LoadableUiState.Error(error.localizedMessage ?: "Unknown error")
                    }
                }
        }
    }
}

@Composable
fun RegistrationScreen(
    uiState: StateFlow<LoadableUiState<out Any>>,
    nameState: TextFieldState,
    surnameState: TextFieldState,
    loginState: TextFieldState,
    passwordState: TextFieldState,
    onSubmit: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        SubtitleText("Регистрация", Modifier.align(BiasAlignment.Horizontal(0f)))

        Spacer(Modifier.height(20.dp))
        InputField("Имя", nameState, !uiState.collectAsState().isLoading())

        Spacer(Modifier.height(10.dp))
        InputField("Фамилия", surnameState, !uiState.collectAsState().isLoading())

        Spacer(Modifier.height(10.dp))
        InputField("Логин", loginState, !uiState.collectAsState().isLoading())

        Spacer(Modifier.height(10.dp))
        InputField("Пароль", passwordState, !uiState.collectAsState().isLoading())

        Spacer(Modifier.weight(1f))

        SquareButton(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            enabled = !nameState.text.isEmpty()
                    && !surnameState.text.isEmpty()
                    && !loginState.text.isEmpty()
                    && !passwordState.text.isEmpty()
                    && !uiState.collectAsState().isLoading(),
            onClick = onSubmit
        ) {
            Text(
                if (uiState.collectAsState()
                        .isLoading()
                ) "Выполняется регистрация..." else "Зарегистрироваться"
            )
            if (uiState.collectAsState().isLoading()) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    AppTheme {
        RegistrationScreen(
            MutableStateFlow(LoadableUiState.Idle).asStateFlow(),
            TextFieldState(),
            TextFieldState(),
            TextFieldState(),
            TextFieldState()
        ) {}
    }
}