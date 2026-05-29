package org.clockwork.tablebooking.ui.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.clockwork.tablebooking.ui.components.LogoBig
import org.clockwork.tablebooking.ui.components.TitleText
import org.clockwork.tablebooking.ui.navigation.Auth
import org.clockwork.tablebooking.ui.theme.AppTheme

@Composable
fun AuthScreen() {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(40.dp))
        LogoBig(modifier = Modifier.align(BiasAlignment.Horizontal(0f)))

        Spacer(Modifier.height(20.dp))
        TitleText("Бронирование столиков")

        val authNavController = rememberNavController()
        NavHost(
            authNavController,
            startDestination = Auth.Login
        ) {
            composable<Auth.Login> {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginContent(
                    loginViewModel.uiState,
                    loginViewModel.loginState,
                    loginViewModel.passwordState,
                    { loginViewModel.performLogin() },
                    {
                        authNavController.navigate(Auth.Registration(
                            loginViewModel.loginState.text as String,
                            loginViewModel.passwordState.text as String
                        ))
                    }
                )
            }
            composable<Auth.Registration> {
                val registrationViewModel: RegistrationViewModel = hiltViewModel()
                RegistrationScreen(
                    registrationViewModel.uiState,
                    registrationViewModel.nameState,
                    registrationViewModel.surnameState,
                    registrationViewModel.loginState,
                    registrationViewModel.passwordState,
                    { registrationViewModel.performRegistration() }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAuthScreen() {
    AppTheme {
        AuthScreen()
    }
}