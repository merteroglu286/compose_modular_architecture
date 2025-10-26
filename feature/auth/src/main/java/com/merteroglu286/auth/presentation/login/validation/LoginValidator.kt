package com.merteroglu286.auth.presentation.login.validation

import com.merteroglu286.auth.presentation.login.error.LoginError

private const val USERNAME_LENGTH = 5
private const val PASSWORD_MIN_LENGTH = 7
private const val PASSWORD_MAX_LENGTH = 10

object LoginValidator {

    fun userNameError(username: String): LoginError {
        return when {
            username.isEmpty() -> LoginError.NoEntry
            !isValidUsernameLength(username) -> LoginError.InCorrectUsernameLength
            !username.isAlphaNumeric() -> LoginError.InCorrectUsername
            else -> LoginError.NoError

        }
    }

    fun passwordError(password: String): LoginError {
        return when {
            password.isEmpty() -> LoginError.NoEntry
            !isValidPasswordLength(password) -> LoginError.InCorrectPasswordLength
            !password.isAlphaNumericWithSpecialCharacters() -> LoginError
                .InCorrectPassword

            else -> LoginError.NoError
        }
    }

    private fun isValidPasswordLength(password: String): Boolean =
        password.count() in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH

    private fun isValidUsernameLength(username: String): Boolean =
        username.count() > USERNAME_LENGTH

    private fun String.isAlphaNumeric() = matches("[a-zA-Z0-9]+".toRegex())

    private fun String.isAlphaNumericWithSpecialCharacters(): Boolean {
        val containsLowerCase = any { it.isLowerCase() }
        val containsUpperCase = any { it.isUpperCase() }
        val containsSpecialCharacter = any { !it.isLetterOrDigit() }
        val containsDigits = any { it.isDigit() }
        return containsLowerCase && containsUpperCase && containsSpecialCharacter && containsDigits
    }

    fun canDoLogin(usernameError: LoginError, passwordError: LoginError) : Boolean {
        return usernameError == LoginError.NoError && passwordError == LoginError.NoError
    }
}