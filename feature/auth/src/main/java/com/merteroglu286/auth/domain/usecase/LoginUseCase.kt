package com.merteroglu286.auth.domain.usecase

import com.merteroglu286.auth.data.source.LoginRemote
import com.merteroglu286.auth.domain.model.User
import com.merteroglu286.domain.result.OutCome
import com.merteroglu286.domain.usecase.AsyncUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRemote: LoginRemote) : AsyncUseCase<LoginUseCase.Input,User>() {
    override suspend fun run(input: LoginUseCase.Input): OutCome<User> {
        return loginRemote.login(username = input.username, password = input.password)
    }

    data class Input(val username: String, val password: String)
}