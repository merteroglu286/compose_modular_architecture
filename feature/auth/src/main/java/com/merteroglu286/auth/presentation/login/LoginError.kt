package com.merteroglu286.auth.presentation.login

import com.merteroglu286.auth.R

sealed class LoginError : Error() {

    abstract fun getErrorMessage(): Int

    data object NoEntry : LoginError() {
        override fun getErrorMessage(): Int = R.string.no_entry
    }

    data object NoError : LoginError() {
        override fun getErrorMessage(): Int = R.string.no_error
    }

    data object InCorrectUsername : LoginError() {
        override fun getErrorMessage(): Int = R.string.username_error

    }

    data object InCorrectPassword : LoginError() {
        override fun getErrorMessage(): Int = R.string.password_error
    }

    data object InCorrectUsernameLength : LoginError() {
        override fun getErrorMessage(): Int = R.string.username_length_error
    }

    data object InCorrectPasswordLength : LoginError() {
        override fun getErrorMessage(): Int = R.string.password_length_error
    }
}