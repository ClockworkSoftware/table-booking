package org.clockwork.tablebooking.ui.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.clockwork.tablebooking.ui.components.InputField
import org.clockwork.tablebooking.ui.theme.primaryLight
import org.clockwork.tablebooking.ui.theme.secondaryLight

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
) {
    val loginState = viewModel.loginState
    val passwordState = viewModel.passwordState
    val uiState = viewModel.uiState

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        InputField("login", loginState, uiState !is AuthUiState.Loading)

        Spacer(Modifier.height(10.dp))

        InputField("login", passwordState, uiState !is AuthUiState.Loading)

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !loginState.text.isEmpty() && !passwordState.text.isEmpty()
                    && uiState !is AuthUiState.Loading,
            onClick = viewModel::performLogin
        ) {
            Row {
                Text(if (uiState is AuthUiState.Loading) "Выполняется вход..." else "Войти")
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }

        when (uiState) {
            is AuthUiState.Success -> Text("All good, ${uiState.user.name}!", color = Color.Green)
            is AuthUiState.Error -> Text("It brokie - $uiState")
            else -> {}
        }
    }
}