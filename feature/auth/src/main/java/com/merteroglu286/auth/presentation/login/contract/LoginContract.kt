package com.merteroglu286.auth.presentation.login.contract

import com.merteroglu286.auth.presentation.login.LoginError
import com.merteroglu286.domain.model.ErrorMessage

// LoginInput
// Kullanıcı etkileşimleri
sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data object OnRegisterClick : LoginEvent()
}

// LoginOutpu
// Tek seferlik UI etkileri (navigasyon, hata mesajı vb.)
sealed class LoginEffect {
    data object NavigateToMain : LoginEffect()
    data object NavigateToRegister : LoginEffect()
    data class ShowError(val error: ErrorMessage) : LoginEffect()
}

// LoginViewState
// UI’nin güncel durumu
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonEnabled: Boolean = false,
    val usernameError: LoginError = LoginError.NoEntry,
    val passwordError: LoginError = LoginError.NoEntry
) {
    fun showUsernameError() = usernameError != LoginError.NoError && usernameError != LoginError.NoEntry
    fun showPasswordError() = passwordError != LoginError.NoError && passwordError != LoginError.NoEntry
}