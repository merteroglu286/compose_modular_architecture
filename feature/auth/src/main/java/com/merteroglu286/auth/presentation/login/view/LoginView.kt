/*
package com.merteroglu286.auth.presentation.login.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.merteroglu286.auth.R
import com.merteroglu286.auth.presentation.login.contract.LoginInput
import com.merteroglu286.auth.presentation.login.contract.LoginOutput
import com.merteroglu286.auth.presentation.login.contract.LoginViewState
import com.merteroglu286.auth.presentation.login.viewmodel.LoginViewModel
import com.merteroglu286.domain.model.toJson
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.HomeDestination
import com.merteroglu286.navigator.destinations.Screens
import com.merteroglu286.presentation.StateRenderer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LoginScreen(appNavigator: AppNavigator) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val stateRenderer by loginViewModel.stateRendererFlow.collectAsState()
    // React to viewOutput events

    LaunchedEffect(loginViewModel) {
        loginViewModel.viewOutput.collect { output ->
            when (output) {
                is LoginOutput.NavigateToMain -> {
                    appNavigator.navigate(
                        HomeDestination.createHome(
                            user = output.user.toJson(),
                            age = 36,
                            userName = output.user.userName,
                        ),
                    )
                }

                is LoginOutput.NavigateToRegister -> {
                    appNavigator.navigate(Screens.SignUpScreenRoute.route)
                }

                is LoginOutput.ShowError -> {
                    TODO()
                }
            }
        }
    }

    // State Renderer

    StateRenderer.of(statRenderer = stateRenderer, retryAction = { loginViewModel.login() }) {
        onUiState { updatedState ->
            ScreeUiContent(updatedState, loginViewModel)
        }
        onLoadingState { _ ->
            // ScreeUiContent(updatedState, loginViewModel)
        }
        onSuccessState { user ->
            val encodedUserJson = URLEncoder.encode(user.toJson(), StandardCharsets.UTF_8.toString())
            appNavigator.navigate(
                HomeDestination.createHome(
                    user = encodedUserJson,
                    age = 36,
                    userName = user.userName
                ),
            )
        }
        onEmptyState {
        }
        onErrorState { _ ->
            // ScreeUiContent(updatedState, loginViewModel)
        }
    }
}

@Composable
fun ScreeUiContent(loginViewState: LoginViewState, loginViewModel: LoginViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CustomTextField(
                label = stringResource(id = R.string.username),
                value = loginViewState.userName,
                errorText = stringResource(id = loginViewState.userNameError.getErrorMessage()),
                showError = loginViewState.showUsernameError(),
            ) { userName ->
                loginViewModel.setInput(LoginInput.UserNameUpdated(userName))
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                label = stringResource(id = R.string.password),
                value = loginViewState.password,
                errorText = stringResource(id = loginViewState.passwordError.getErrorMessage()),
                showError = loginViewState.showPasswordError(),
            ) { password ->
                loginViewModel.setInput(LoginInput.PasswordUpdated(password))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { loginViewModel.login() },
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { loginViewModel.setInput(LoginInput.RegisterButtonClicked) }) {
                Text(text = "Sign up Now!")
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
    onChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChanged(it) },
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        isError = showError,
        visualTransformation = visualTransformation,
    )
    if (showError) {
        Text(
            text = errorText,
            color = Color.Red,
            modifier = Modifier.padding(all = 8.dp),
        )
    }
}*/

package com.merteroglu286.auth.presentation.login.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.merteroglu286.auth.R
import com.merteroglu286.auth.presentation.login.contract.LoginEvent
import com.merteroglu286.auth.presentation.login.contract.LoginEffect
import com.merteroglu286.auth.presentation.login.contract.LoginUiState
import com.merteroglu286.auth.presentation.login.viewmodel.LoginViewModel
import com.merteroglu286.domain.model.toJson
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.HomeDestination
import com.merteroglu286.navigator.destinations.Screens
import com.merteroglu286.presentation.ScreenState
import com.merteroglu286.presentation.views.EmptyScreen
import com.merteroglu286.presentation.views.ErrorFullScreen
import com.merteroglu286.presentation.views.LoadingFullScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LoginScreen(appNavigator: AppNavigator) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val screenState by loginViewModel.screenStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        loginViewModel.effectFlow.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToMain -> {
                    val encodedUserJson = URLEncoder.encode(effect.user.toJson(), StandardCharsets.UTF_8.toString())
                    appNavigator.navigate(
                        HomeDestination.createHome(
                            user = encodedUserJson,
                            age = 36,
                            userName = effect.user.userName,
                        ),
                    )
                }
                is LoginEffect.NavigateToRegister -> appNavigator.navigate(Screens.SignUpScreenRoute.route)
                is LoginEffect.ShowError -> {/* UI'de error göstermek için, Snackbar vs. */}
            }
        }
    }

    when (screenState) {
        is ScreenState.Loading -> {
            val loading = screenState as ScreenState.Loading<*, *>
            LoadingFullScreen(loading.loadingMessage)
        }
        is ScreenState.Error -> {
            val error = screenState as ScreenState.Error<*, *>
            ErrorFullScreen(error.errorMessage) { loginViewModel.login() }
        }
        is ScreenState.Empty -> {
            val empty = screenState as ScreenState.Empty<*, *>
            EmptyScreen(empty.emptyMessage)
        }
        is ScreenState.Success -> {
            // Success state => navigasyon veya başka UI işlemleri
        }
        is ScreenState.Content -> {
            val content = screenState as ScreenState.Content<LoginUiState, *>
            ScreeUiContent(content.uiState, loginViewModel)
        }
    }
}

@Composable
fun ScreeUiContent(uiState: LoginUiState, loginViewModel: LoginViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CustomTextField(
                label = stringResource(id = R.string.username),
                value = uiState.userName,
                errorText = stringResource(id = uiState.userNameError.getErrorMessage()),
                showError = uiState.showUsernameError(),
            ) { userName ->
                loginViewModel.onEvent(LoginEvent.UserNameUpdated(userName))
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                label = stringResource(id = R.string.password),
                value = uiState.password,
                errorText = stringResource(id = uiState.passwordError.getErrorMessage()),
                showError = uiState.showPasswordError(),
            ) { password ->
                loginViewModel.onEvent(LoginEvent.PasswordUpdated(password))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { loginViewModel.login() },
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { loginViewModel.onEvent(LoginEvent.RegisterButtonClicked) }) {
                Text(text = "Sign up Now!")
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
    onChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChanged(it) },
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        isError = showError,
        visualTransformation = visualTransformation,
    )
    if (showError) {
        Text(
            text = errorText,
            color = Color.Red,
            modifier = Modifier.padding(all = 8.dp),
        )
    }
}