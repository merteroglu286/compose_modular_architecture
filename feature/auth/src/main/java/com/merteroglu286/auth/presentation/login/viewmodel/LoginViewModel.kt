/*
package com.merteroglu286.auth.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merteroglu286.domain.model.User
import com.merteroglu286.auth.domain.usecase.LoginUseCase
import com.merteroglu286.auth.presentation.login.contract.LoginInput
import com.merteroglu286.auth.presentation.login.contract.LoginOutput
import com.merteroglu286.auth.presentation.login.contract.LoginViewState
import com.merteroglu286.auth.presentation.login.error.LoginError
import com.merteroglu286.auth.presentation.login.validation.LoginValidator
import com.merteroglu286.presentation.StateRenderer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var loginViewState = LoginViewState()

    private val _stateRendererMutableState = MutableStateFlow<StateRenderer<LoginViewState, User>>(
        StateRenderer.ScreenContent(loginViewState),
    )

    val stateRendererFlow: StateFlow<StateRenderer<LoginViewState, User>> get() = _stateRendererMutableState

    // output of viewmodel
    private val _viewOutPut: Channel<LoginOutput> = Channel()
    val viewOutput = _viewOutPut.receiveAsFlow()

    fun setInput(input: LoginInput) {
        when (input) {
            is LoginInput.LoginButtonClicked -> login()
            is LoginInput.PasswordUpdated -> updateState { copy(password = input.password) }
            is LoginInput.RegisterButtonClicked -> sendOutput { LoginOutput.NavigateToRegister }
            is LoginInput.UserNameUpdated -> updateState { copy(userName = input.username) }
        }
    }

    private fun updateState(updateState: LoginViewState.() -> LoginViewState) {
        loginViewState = loginViewState.updateState()
        validate()
    }

    private fun validate() {
        val userNameError: LoginError = LoginValidator.userNameError(loginViewState.userName)
        val passwordError: LoginError = LoginValidator.passwordError(loginViewState.password)
        val isLoginButtonEnabled: Boolean = LoginValidator.canDoLogin(userNameError, passwordError)

        loginViewState = loginViewState.copy(
            isLoginButtonEnabled = isLoginButtonEnabled,
            userNameError = userNameError,
            passwordError = passwordError,
        )

        val newStateRenderer = StateRenderer.ScreenContent<LoginViewState, User>(loginViewState)
        _stateRendererMutableState.value = newStateRenderer
    }

    private fun sendOutput(action: () -> LoginOutput) {
        viewModelScope.launch {
            _viewOutPut.send(action())
        }
    }

    fun login() {
        viewModelScope.launch {
            // loading popup state
            val newStateRenderer = StateRenderer.LoadingFullScreen<LoginViewState, User>(loginViewState)
            _stateRendererMutableState.value = newStateRenderer
            loginUseCase.execute(
                LoginUseCase.Input(
                    username = loginViewState.userName,
                    password = loginViewState.password,
                ),
                success = {
                    // loading popup state
                    val newStateRenderer = StateRenderer.Success<LoginViewState, User>(it)
                    _stateRendererMutableState.value = newStateRenderer
                },
                error = {
                    // loading popup state
                    val newStateRenderer =
                        StateRenderer.ErrorFullScreen<LoginViewState, User>(loginViewState, it)
                    _stateRendererMutableState.value = newStateRenderer
                },
            )
        }
    }
}*/

package com.merteroglu286.auth.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merteroglu286.auth.domain.usecase.LoginUseCase
import com.merteroglu286.auth.presentation.login.contract.LoginEvent
import com.merteroglu286.auth.presentation.login.contract.LoginEffect
import com.merteroglu286.auth.presentation.login.contract.LoginUiState
import com.merteroglu286.auth.presentation.login.error.LoginError
import com.merteroglu286.auth.presentation.login.validation.LoginValidator
import com.merteroglu286.domain.model.User
import com.merteroglu286.presentation.R
import com.merteroglu286.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var loginUiState = LoginUiState()
    private val _screenStateFlow = MutableStateFlow<ScreenState<LoginUiState, User>>(
        ScreenState.ScreenContent(loginUiState)
    )
    val screenStateFlow: StateFlow<ScreenState<LoginUiState, User>> get() = _screenStateFlow

    // Tekil efektleri yönetmek için SharedFlow
    private val _effectFlow = MutableSharedFlow<LoginEffect>(replay = 0)
    val effectFlow: SharedFlow<LoginEffect> = _effectFlow.asSharedFlow()

    // Eventler için fonksiyon
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UserNameUpdated -> updateState { copy(userName = event.username) }
            is LoginEvent.PasswordUpdated -> updateState { copy(password = event.password) }
            is LoginEvent.LoginButtonClicked -> login()
            is LoginEvent.RegisterButtonClicked -> sendEffect(LoginEffect.NavigateToRegister)
        }
    }

    private fun updateState(state: LoginUiState.() -> LoginUiState) {
        loginUiState = loginUiState.state()
        validate()
    }

    private fun validate() {
        val userNameError: LoginError = LoginValidator.userNameError(loginUiState.userName)
        val passwordError: LoginError = LoginValidator.passwordError(loginUiState.password)
        val isLoginButtonEnabled: Boolean = LoginValidator.canDoLogin(userNameError, passwordError)

        loginUiState = loginUiState.copy(
            isLoginButtonEnabled = isLoginButtonEnabled,
            userNameError = userNameError,
            passwordError = passwordError,
        )
        _screenStateFlow.value = ScreenState.ScreenContent(loginUiState)
    }

    private fun sendEffect(effect: LoginEffect) {
        viewModelScope.launch { _effectFlow.emit(effect) }
    }

    fun login() {
        viewModelScope.launch {
            _screenStateFlow.value = ScreenState.LoadingFullScreen(loginUiState, R.string.loading)
            loginUseCase.execute(
                LoginUseCase.Input(loginUiState.userName, loginUiState.password),
                success = { user ->
                    _screenStateFlow.value = ScreenState.Success(user)
                    sendEffect(LoginEffect.NavigateToMain(user))
                },
                error = { errorMessage ->
                    _screenStateFlow.value = ScreenState.ErrorFullScreen(loginUiState, errorMessage)
                    sendEffect(LoginEffect.ShowError(errorMessage))
                }
            )
        }
    }
}