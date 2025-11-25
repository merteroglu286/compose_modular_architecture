package com.merteroglu286.auth.data.mapper

import com.merteroglu286.auth.data.responses.LoginResponse
import com.merteroglu286.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoginMapperImpl(private val defaultDispatcher: CoroutineDispatcher) : LoginMapper {
    override suspend fun toDomain(loginResponse: LoginResponse): User {
        return withContext(defaultDispatcher) {
            User(
                id = loginResponse.id.orEmpty(),
                userName = loginResponse.userName.orEmpty(),
                email = loginResponse.email.orEmpty(),
                photo = loginResponse.photo.orEmpty()
            )
        }
    }
}