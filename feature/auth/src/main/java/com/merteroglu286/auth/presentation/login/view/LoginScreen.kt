package com.merteroglu286.auth.presentation.login.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.merteroglu286.auth.R
import com.merteroglu286.auth.presentation.login.contract.LoginEffect
import com.merteroglu286.auth.presentation.login.contract.LoginEvent
import com.merteroglu286.auth.presentation.login.contract.LoginUiState
import com.merteroglu286.auth.presentation.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(uiState: LoginUiState, loginViewModel: LoginViewModel) {

    LaunchedEffect(loginViewModel) {
        loginViewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToMain -> {
                    // Navigate to main screen
                }

                is LoginEffect.NavigateToRegister -> {
                    // Navigate to register screen
                }

                is LoginEffect.ShowError -> {
                    // Show error message
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomTextField(
                label = stringResource(R.string.username),
                value = uiState.username,
                errorText = stringResource(id = uiState.usernameError.getErrorMessage()),
                showError = uiState.showUsernameError()
            ) { username ->
                loginViewModel.onEvent(LoginEvent.UsernameChanged(username))
            }

            Spacer(modifier = Modifier.padding(16.dp))

            CustomTextField(
                label = stringResource(R.string.password),
                value = uiState.password,
                errorText = stringResource(id = uiState.passwordError.getErrorMessage()),
                showError = uiState.showPasswordError()
            ) { password ->
                loginViewModel.onEvent(LoginEvent.PasswordChanged(password))
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    loginViewModel.login()
                }
            ) {
                Text("Login")
            }
        }
    }
}


@Composable
fun CustomTextField(
    label: String,
    value: String,
    showError: Boolean,
    errorText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChanged(it) },
        label = { Text(text = label) },
        isError = showError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        visualTransformation = visualTransformation
    )
    if (showError) {
        Text(
            text = errorText, color = Color.Red,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}