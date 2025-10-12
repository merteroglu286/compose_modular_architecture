package com.merteroglu286.auth.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merteroglu286.auth.domain.usecase.LoginUseCase
import com.merteroglu286.auth.presentation.login.contract.LoginEvent
import com.merteroglu286.auth.presentation.login.contract.LoginEffect
import com.merteroglu286.auth.presentation.login.contract.LoginUiState
import com.merteroglu286.auth.presentation.login.validation.LoginValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> login()
            is LoginEvent.PasswordChanged -> updateState { copy(password = event.password) }
            is LoginEvent.OnRegisterClick -> sendEffect(LoginEffect.NavigateToRegister)
            is LoginEvent.UsernameChanged -> updateState { copy(username = event.username) }
        }
    }

    private fun updateState(transform: LoginUiState.() -> LoginUiState) {
        _uiState.update { current ->
            val updated = current.transform()
            updated.validate()
        }
    }

    private fun LoginUiState.validate(): LoginUiState {
        val usernameError = LoginValidator.usernameError(username)
        val passwordError = LoginValidator.passwordError(password)
        val isEnabled = LoginValidator.canDoLogin(usernameError, passwordError)

        return copy(
            usernameError = usernameError,
            passwordError = passwordError,
            isLoginButtonEnabled = isEnabled
        )
    }


    private fun sendEffect(effect: LoginEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun login() {
        viewModelScope.launch {
            loginUseCase.execute(
                LoginUseCase.Input(
                    username = uiState.value.username,
                    password = uiState.value.password
                ),
                success = { user ->
                },
                error = {
                }
            )
        }
    }
}
